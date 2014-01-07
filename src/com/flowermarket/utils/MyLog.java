package com.flowermarket.utils;

import android.util.Log;

public class MyLog {

	public final static String TAG = "diandi";
	public final static String TAG_REQUEST = TAG + "_request";
	public final static String TAG_RESPONSE = TAG + "_response";

	public static void requestLog(String msg) {
		Log.d(TAG_REQUEST, msg);
	}

	public static void responseLog(String msg) {
		Log.d(TAG_RESPONSE, msg);
	}

	public static void debugLog(String msg) {
		Log.d(TAG, msg);
	}

}
