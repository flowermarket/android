package com.flowermarket.http.base;

public class HttpUrls {

	public final static String ip = "http://59.42.238.84:8282";

	public final static String url = ip + "/Google/";

	public final static String createQuestUrl(String action) {
		return url + action;
	}

}
