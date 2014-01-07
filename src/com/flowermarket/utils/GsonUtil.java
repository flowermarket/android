package com.flowermarket.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	private static GsonUtil instance;

	public static GsonUtil getInstance() {
		if (instance == null)
			instance = new GsonUtil();
		return instance;
	}

	private Gson gson;

	public GsonUtil() {
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
	}

	public String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public Gson getGson() {
		return gson;
	}

}
