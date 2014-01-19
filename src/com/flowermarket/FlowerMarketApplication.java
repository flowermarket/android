package com.flowermarket;

import android.app.Application;

import com.flowermarket.entitys.User;

public class FlowerMarketApplication extends Application {

	public static User user;

	public static String uuid;

	@Override
	public void onCreate() {
		super.onCreate();
		user = new User();
		user.uid = "130";
		user.userName = "哈哈哈";
	}

}
