package com.flowermarket.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.commpoments.MessageAdapter;
import com.flowermarket.entitys.responses.GetMessageResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;

public class MessageListActivity extends BaseActivity {

	private ListView list;
	private MessageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);
		list = (ListView) findViewById(R.id.list);
		adapter = new MessageAdapter(this);
		list.setAdapter(adapter);

		getMessage();
	}

	private void getMessage() {
		HttpRequestEntity request = new HttpRequestEntity("android!gainMsg",
				GetMessageResponse.class);
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);
		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
						GetMessageResponse response = (GetMessageResponse) resp;
						adapter.setData(response.data);
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {

					}
				});
	}

}
