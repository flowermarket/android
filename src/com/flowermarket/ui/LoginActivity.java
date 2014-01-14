package com.flowermarket.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.entitys.responses.UserLoginResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.utils.CacheUtil;
import com.flowermarket.utils.Tools;

public class LoginActivity extends BaseActivity implements OnClickListener,
		OnKeyListener {

	private EditText account;
	private EditText password;
	private View loginBtn;
	private View exitBtn;
	private CheckBox remember;

	private ProgressDialog dialog;
	private boolean logined;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		account = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
		loginBtn = findViewById(R.id.loginBtn);
		exitBtn = findViewById(R.id.exitBtn);
		remember = (CheckBox) findViewById(R.id.remember);
		password.setOnKeyListener(this);

		loginBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);

		String value = CacheUtil.getInstance(this).get("user_login_cache");
		if (value != null) {
			account.setText(value);
			account.setSelection(account.length());
			remember.setChecked(true);
		}

	}

	private void showProgress() {
		if (dialog == null) {
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在登录中...");
		}
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		if (v == exitBtn) {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
		} else if (v == loginBtn) {
			handleLogin();
		}
	}

	private void handleLogin() {
		if (account.getText().toString().trim().length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入用户名",
					Toast.LENGTH_SHORT).show();
		} else if (password.getText().toString().trim().length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入 密码",
					Toast.LENGTH_SHORT).show();
		} else if (!logined) {
			login();
		}
	}

	private void login() {
		showProgress();
		logined = true;
		FlowerMarketApplication.uuid = Tools.createUUID();
		HttpRequestEntity request = new HttpRequestEntity("android!login",
				UserLoginResponse.class);
		request.addParam("userName", account.getText().toString().trim());
		request.addParam("passWord", password.getText().toString().trim());
		request.addParam("uuid", FlowerMarketApplication.uuid);

		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
						dialog.dismiss();
						FlowerMarketApplication.user = ((UserLoginResponse) resp).data;
						if (remember.isChecked()) {
							CacheUtil.getInstance(getApplicationContext()).put(
									"user_login_cache",
									account.getText().toString().trim());
						}
						Toast.makeText(getApplicationContext(), "登录成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(),
								HomeActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {
						dialog.dismiss();
						logined = false;
						if (entity != null && entity.msg != null) {
							Toast.makeText(getApplicationContext(), entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "登录失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (v == password) {
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				handleLogin();
			}
		}
		return false;
	}

}
