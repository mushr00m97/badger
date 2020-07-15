package com.mushr00m.utils;

import java.util.regex.Pattern;

public class VerifyUtils {
	/**
	 * 正则表达式：验证用户名[长度6-18]
	 */
	private static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

	/**
	 * 正则表达式：验证密码[长度6-16]
	 */
	private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * 正则表达式：验证手机号
	 */
	// private static final String REGEX_MOBILE =
	// "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	private static final String REGEX_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

	/**
	 * 正则表达式：验证汉字[长度1]
	 */
	private static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * 正则表达式：验证身份证
	 */
	private static final String REGEX_ID_CARD = "^\\d{15}|^\\d{17}([0-9]|X|x)$";

	/**
	 * 正则表达式：验证URL
	 */
	private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 正则表达式：验证IP地址
	 */
	private static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	// 验证非空
	public static boolean isNullOrEmpty(String txt) {
		if (null == txt)
			return true;
		if (txt.trim().equals(""))
			return true;
		return false;
	}

	// 验证长度
	public static boolean isInLength(String txt, int minLength, int maxLength) {
		if (isNullOrEmpty(txt))
			return false;
		if (txt.trim().length() < minLength || txt.trim().length() > maxLength)
			return false;
		return true;
	}

	// 验证纯小写字母str
	public static boolean isLowerStr(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		char[] chs = txt.trim().toCharArray();
		for (char ch : chs) {
			if (ch < 'a' || ch > 'z')
				return false;
		}
		return true;
	}

	// 验证纯大写字母str
	public static boolean isUpperStr(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		char[] chs = txt.trim().toCharArray();
		for (char ch : chs) {
			if (ch < 'A' || ch > 'Z')
				return false;
		}
		return true;
	}

	// 验证纯字母str
	public static boolean isEngStr(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		char[] chs = txt.trim().toCharArray();
		for (char ch : chs) {
			if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z'))
				return false;
		}
		return true;
	}

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		return Pattern.matches(REGEX_USERNAME, txt.trim());
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		return Pattern.matches(REGEX_PASSWORD, txt.trim());
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		// return Pattern.matches(REGEX_MOBILE, txt.trim());
		if (txt.trim().length() != 11)
			return false;
		return true;
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		if (txt.trim().length() > 80)
			return false;
		return Pattern.matches(REGEX_EMAIL, txt.trim());
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		return Pattern.matches(REGEX_CHINESE, txt.trim());
	}

	/**
	 * 校验中文姓名
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChineseName(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		if (txt.trim().length() < 1 || txt.trim().length() > 25)
			return false;
		String name = txt.trim().replace(".", "").replace("-", "").trim();
		char[] arr = name.toCharArray();
		for (char c : arr) {
			if (!Pattern.matches(REGEX_CHINESE, c + ""))
				return false;
		}
		return true;
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		return Pattern.matches(REGEX_ID_CARD, txt.trim());
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		return Pattern.matches(REGEX_URL, txt.trim());
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String txt) {
		if (isNullOrEmpty(txt))
			return false;
		return Pattern.matches(REGEX_IP_ADDR, txt.trim());
	}

	//
}
