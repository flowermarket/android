package com.flowermarket.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Tools {

	private static SimpleDateFormat dateFormat;
	private static HanyuPinyinOutputFormat pinyinformat;

	static {
		dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		dateFormat.applyPattern("yyyy-MM-dd");
		pinyinformat = new HanyuPinyinOutputFormat();
	}

	public static String createUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getTimeString(Date date) {
		return dateFormat.format(date);
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
}
