package com.flowermarket.http;

import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import android.os.Handler;

import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.http.base.HttpUrls;
import com.flowermarket.utils.GsonUtil;
import com.flowermarket.utils.MyLog;

public class HttpRequestSession {

	private static HttpRequestSession instance;

	public static HttpRequestSession getInstance() {
		if (instance == null)
			instance = new HttpRequestSession();
		return instance;
	}

	private Handler handler = new Handler();

	public void requestSession(final HttpRequestEntity requestEntity,
			final OnRequestCallback callback) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				request(requestEntity, callback);
			}
		}.start();
	}

	private void request(HttpRequestEntity requestEntity,
			final OnRequestCallback callback) {
		try {
			String url = HttpUrls.createQuestUrl(requestEntity.action);
			MyLog.requestLog(url + requestEntity.toString());
			HttpPost post = new HttpPost(url);
			if (requestEntity.params.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						requestEntity.params, "utf-8");
				post.setEntity(entity);
			}
			HttpClient client = HttpClientPool.getInstance().getHttpClient();
			HttpResponse response = client.execute(post);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String result = URLDecoder.decode(
						EntityUtils.toString(response.getEntity()), "utf-8");
				MyLog.responseLog(result);
				final HttpResponseEntity resp = GsonUtil.getInstance()
						.getGson().fromJson(result, requestEntity.respCls);
				if (resp.error_code == 0) {
					if (callback != null) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								callback.onSuccess(resp);
							}
						});
					}
				} else {
					if (callback != null) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								callback.onFailue(statusCode, resp);
							}
						});
					}
				}
			} else {
				if (callback != null) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							HttpResponseEntity resp = new HttpResponseEntity();
							resp.msg = "请求错误";
							resp.error_code = -1;
							callback.onFailue(statusCode, resp);
						}
					});
				}
				MyLog.responseLog("error:" + statusCode);
			}
			HttpClientPool.getInstance().callbackClient(client);
		} catch (Exception e) {
			e.printStackTrace();
			if (callback != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						HttpResponseEntity resp = new HttpResponseEntity();
						resp.msg = "请求错误";
						resp.error_code = -1;
						callback.onFailue(-1, resp);
					}
				});
			}
		}
	}

	public interface OnRequestCallback {
		public void onSuccess(HttpResponseEntity resp);

		public void onFailue(int statusCode, HttpResponseEntity entity);
	}

}
