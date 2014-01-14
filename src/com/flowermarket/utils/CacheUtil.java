package com.flowermarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtil {

	private SharedPreferences shared;

	private static CacheUtil instance;

	public static CacheUtil getInstance(Context context) {
		if (instance == null)
			instance = new CacheUtil(context);
		return instance;
	}

	public CacheUtil(Context context) {
		shared = context.getSharedPreferences("flower", Context.MODE_PRIVATE);
	}

	public void put(String key, String value) {
		shared.edit().putString(key, value).commit();
	}

	public String get(String key) {
		return shared.getString(key, null);
	}

}
