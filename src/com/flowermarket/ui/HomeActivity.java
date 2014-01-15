package com.flowermarket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.utils.UserBarTools;

public class HomeActivity extends BaseActivity implements OnClickListener {

	private View contact;
	private View setting;
	private View plan;
	private View security;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		UserBarTools.setUserBarValue(findViewById(R.id.userBar));

		contact = findViewById(R.id.contact);
		contact.setOnClickListener(this);
		setting = findViewById(R.id.setting);
		setting.setOnClickListener(this);
		plan = findViewById(R.id.plan);
		plan.setOnClickListener(this);
		security = findViewById(R.id.security);
		security.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v == contact) {
			Intent intent = new Intent(this, ContactListActivity.class);
			startActivity(intent);
		} else if (v == setting) {
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
		} else if (v == plan) {
			Intent intent = new Intent(this, PlanListActivity.class);
			startActivity(intent);
		} else if (v == security) {
			Intent intent = new Intent(this, SecurityActivity.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		UserBarTools.setUserBarValue(findViewById(R.id.userBar));
	}

}
