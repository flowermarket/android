package com.flowermarket.utils;

import java.util.Date;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;

import android.view.View;
import android.widget.TextView;

public class UserBarTools {

	public static void setUserBarValue(View userBar) {
		TextView userName = (TextView) userBar.findViewById(R.id.userName);
		TextView loginTime = (TextView) userBar.findViewById(R.id.loginTime);

		if (FlowerMarketApplication.user != null) {
			userName.setText("用户名：" + FlowerMarketApplication.user.userName);
		}
		loginTime.setText("登录时间：" + Tools.getTimeString(new Date()));
	}

}
