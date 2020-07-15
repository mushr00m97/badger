package com.mushr00m.utils;

public class MyStrUtils {
	// 按长度截取字符串
	public static String substr(String str, int len) {
		if (null == str)
			return null;
		return str.length() > len ? str.substring(0, len) : str;
	}

	// 按起始位和长度截取字符串
	public static String substr(String str, int start, int len) {
		if (null == str)
			return null;
		return str.length() > len ? str.substring(start, start + len) : str;
	}
}
