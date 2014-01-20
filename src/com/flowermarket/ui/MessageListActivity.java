package com.flowermarket.ui;

import java.util.Date;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.commpoments.MessageAdapter;
import com.flowermarket.entitys.Message;
import com.flowermarket.entitys.responses.GetMessageResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.services.MessageServices;
import com.flowermarket.utils.Tools;

public class MessageListActivity extends BaseActivity implements
		OnClickListener {

	private ListView list;
	private MessageAdapter adapter;
	private EditText input;
	private View sendBtn;

	private ProgressDialog progress;

	public final static String MESSAGE_ACTIVITY_RECIEVE = "messageRecieve";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);
		list = (ListView) findViewById(R.id.list);
		adapter = new MessageAdapter(this);
		list.setAdapter(adapter);
		input = (EditText) findViewById(R.id.input);
		sendBtn = findViewById(R.id.sendBtn);
		sendBtn.setOnClickListener(this);

		IntentFilter filter = new IntentFilter(MESSAGE_ACTIVITY_RECIEVE);
		registerReceiver(receiver, filter);

		Intent intent = new Intent(MessageServices.MESSAGE_ACTION_GETMESSAGE);
		intent.putExtra("action", MESSAGE_ACTIVITY_RECIEVE);
		sendBroadcast(intent);

		intent = new Intent(MessageServices.MESSAGE_ACTION_REGISTACTION);
		intent.putExtra("action", MESSAGE_ACTIVITY_RECIEVE);
		sendBroadcast(intent);

		getMessage();
	}

	private void showProgress() {
		if (progress == null) {
			progress = new ProgressDialog(this);
			progress.setMessage("正在加载历史消息中...");
		}
		progress.show();
	}

	private void getMessage() {
		showProgress();
		if (FlowerMarketApplication.user != null) {
			HttpRequestEntity request = new HttpRequestEntity(
					"android!gainHtyMsg", GetMessageResponse.class);
			request.addParam("uid", FlowerMarketApplication.user.uid);
			request.addParam("uuid", FlowerMarketApplication.uuid);
			request.addParam("TimeMillis", Tools.getTimeString(new Date()));

			HttpRequestSession.getInstance().requestSession(request,
					new OnRequestCallback() {

						@Override
						public void onSuccess(HttpResponseEntity resp) {
							progress.dismiss();
							adapter.setData(((GetMessageResponse) resp).data);
						}

						@Override
						public void onFailue(int statusCode,
								HttpResponseEntity entity) {
							progress.dismiss();
							if (entity != null && entity.msg != null) {
								Toast.makeText(getApplicationContext(),
										entity.msg, Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(), "请求失败",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
		}
	}

	private void sendMessage() {
		Message message = new Message();
		message.uid = FlowerMarketApplication.user.uid;
		message.message = input.getText().toString().trim();
		message.time = Tools.getDateTimeString(new Date());
		HttpRequestEntity request = new HttpRequestEntity("android!sendMsg");
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);
		request.addParam("message", input.getText().toString().trim());
		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {
						if (entity != null && entity.msg != null) {
							Toast.makeText(getApplicationContext(), entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "请求数据失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		if (v == sendBtn) {
			if (input.getText().toString().trim().length() == 0) {
				Toast.makeText(this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
			} else {
				sendMessage();
				input.setText("");
			}
		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			GetMessageResponse response = (GetMessageResponse) intent
					.getSerializableExtra("response");
			Log.d("Test", "response:");
			if (response != null) {
				Log.d("Test", "response:" + response.data.size());
				adapter.appendData(response.data);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

}
