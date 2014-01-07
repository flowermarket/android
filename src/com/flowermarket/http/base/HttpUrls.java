package com.flowermarket.http.base;

public class HttpUrls {

	public final static String ip = "http://192.168.1.101:8080/";

	public final static String url = ip + "diandi/";

	public final static String createQuestUrl(String action) {
		return url + action;
	}

}
