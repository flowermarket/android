package com.flowermarket.http.base;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequestEntity {

	public String action;
	public ArrayList<NameValuePair> params;
	public Class<? extends HttpResponseEntity> respCls;

	public HttpRequestEntity(String action) {
		params = new ArrayList<NameValuePair>();
		this.action = action;
		this.respCls = HttpResponseEntity.class;
	}

	public HttpRequestEntity(String action,
			Class<? extends HttpResponseEntity> cls) {
		params = new ArrayList<NameValuePair>();
		this.action = action;
		this.respCls = cls;
	}

	public void addParam(String key, Object value) {
		params.add(new BasicNameValuePair(key, value.toString()));
	}

	public String toString() {
		if (params == null)
			return null;
		StringBuffer buffer = new StringBuffer("?");
		for (int i = 0; i < params.size(); i++) {
			NameValuePair nvp = params.get(i);
			buffer.append(nvp.getName());
			buffer.append("=");
			buffer.append(nvp.getValue());
			buffer.append("&");
		}
		return buffer.toString();
	}

}
