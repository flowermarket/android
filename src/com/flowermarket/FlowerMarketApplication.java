package com.flowermarket;

import android.app.Application;
import android.content.Intent;

import com.flowermarket.entitys.User;
import com.flowermarket.services.MessageServices;

public class FlowerMarketApplication extends Application {

	public static User user;

	public static String uuid;

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
