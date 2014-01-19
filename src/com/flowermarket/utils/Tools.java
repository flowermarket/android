package com.flowermarket.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Tools {

	private static SimpleDateFormat dateFormat;
	private static SimpleDateFormat dateTimeFormat;
	private static HanyuPinyinOutputFormat pinyinformat;

	static {
		dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		dateFormat.applyPattern("yyyy-MM-dd");
		dateTimeFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		dateTimeFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		pinyinformat = new HanyuPinyinOutputFormat();
	}

	public static String createUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getTimeString(Date date) {
		return dateFormat.format(date);
	}

	public static String getDateTimeString(Date date) {
		return dateTimeFormat.format(date);
	}

	/**
	 * 根据汉字拿到拼音
	 * 
	 * @param str
	 * @return
	 */
	public static String getStringPinYin(String str) {
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin == null) {
				// 如果str.charAt(i)非汉字，则保持原样
				sb.append(str.charAt(i));
			} else {
				sb.append(tempPinyin);
			}
		}
		return sb.toString();
	}

	public static String getCharacterPinYin(char c) {
		String[] pinyin = null;
		pinyinformat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, pinyinformat);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		// 如果c不是汉字，toHanyuPinyinStringArray会返回null
		if (pinyin == null) {
			return null;
		} else {
			// 只取一个发音，如果是多音字，仅取第一个发音
			return pinyin[0];
		}

	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPenGPS(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (gps) {
			return true;
		}

		return false;
	}

	/**
	 * 强制帮用户打开GPS
	 * 
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}
}
