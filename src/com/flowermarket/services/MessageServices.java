package com.flowermarket.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.entitys.responses.GetMessageResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;

public class MessageServices extends Service {

	private Handler handler = new Handler();

	private final static long MSG_REQUEST_DELAY = 3000;
	private boolean destroyed;
	private GetMessageResponse response;
	private String targetAction;

	public final static String MESSAGE_ACTION_GETMESSAGE = "getmessage";
	public final static String MESSAGE_ACTION_REGISTACTION = "regist_action";

	private HttpRequestEntity request;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MESSAGE_ACTION_GETMESSAGE);
		filter.addAction(MESSAGE_ACTION_REGISTACTION);
		registerReceiver(receiver, filter);
		request = new HttpRequestEntity("android!gainMsg",
				GetMessageResponse.class);
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				getMessage();
				if (!destroyed) {
					handler.postDelayed(this, MSG_REQUEST_DELAY);
				}
			}
		}, MSG_REQUEST_DELAY);
	}

	private void getMessage() {
		if (FlowerMarketApplication.user != null) {

			HttpRequestSession.getInstance().requestSession(request, callback);
		}
	}

	private OnRequestCallback callback = new OnRequestCallback() {

		@Override
		public void onSuccess(HttpResponseEntity resp) {
			response = (GetMessageResponse) resp;
			if (targetAction != null) {
				Intent data = new Intent(targetAction);
				data.putExtra("response", response);
				sendBroadcast(data);
			}
		}

		@Override
		public void onFailue(int statusCode, HttpResponseEntity entity) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		destroyed = true;
		unregisterReceiver(receiver);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(MESSAGE_ACTION_REGISTACTION)) {
				targetAction = intent.getStringExtra("action");
			} else if (action.equals(MESSAGE_ACTION_GETMESSAGE)) {
				Intent data = new Intent(intent.getStringExtra("action"));
				data.putExtra("response", response);
				sendBroadcast(data);
			}
		}
	};
}
