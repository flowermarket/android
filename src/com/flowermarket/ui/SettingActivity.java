package com.flowermarket.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.utils.UserBarTools;

public class SettingActivity extends BaseActivity implements OnClickListener {

	private View exitBtn;
	private View cancelInput;
	private View logoutBtn;
	private View modifyInput;
	private View saveInput;
	private EditText newpassword;
	private EditText repeatpassword;

	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);

		UserBarTools.setUserBarValue(findViewById(R.id.userBar));

		exitBtn = findViewById(R.id.exitBtn);
		cancelInput = findViewById(R.id.cancelInput);
		cancelInput.setOnClickListener(this);
		exitBtn.setOnClickListener(this);

		logoutBtn = findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(this);

		newpassword = (EditText) findViewById(R.id.newpassword);
		repeatpassword = (EditText) findViewById(R.id.repeatpassword);
		modifyInput = findViewById(R.id.modifyInput);
		modifyInput.setOnClickListener(this);
		saveInput = findViewById(R.id.saveInput);
		saveInput.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == cancelInput || v == exitBtn) {
			finish();
		} else if (v == logoutBtn) {
			FlowerMarketApplication.user = null;
			FlowerMarketApplication.uuid = null;
			Intent intent = new Intent(this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		} else if (v == modifyInput) {
			newpassword.setText("");
			repeatpassword.setText("");
		} else if (v == saveInput) {
			if (newpassword.length() == 0) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				newpassword.requestFocus();
			} else if (!repeatpassword.getText().toString()
					.equals(newpassword.getText().toString())) {
				Toast.makeText(this, "重复密码不一致", Toast.LENGTH_SHORT).show();
			} else {
				modifyPassword();
			}
		}
	}

	private void showProgress() {
		if (progress == null) {
			progress = new ProgressDialog(this);
			progress.setMessage("正在修改密码中，请稍候...");
		}
		progress.show();
	}

	private void modifyPassword() {
		showProgress();
		HttpRequestEntity request = new HttpRequestEntity("android!setPassword");
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);
		request.addParam("passWord", newpassword.getText().toString());
		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
						progress.dismiss();
						Toast.makeText(getApplicationContext(), "修改密码成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {
						progress.dismiss();
						if (entity != null && entity.msg != null) {
							Toast.makeText(getApplicationContext(), entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "修改密码失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

}
