package com.mushr00m.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
	// 获取properties对象
	public static Properties getResourceAsProperties(String resource) {
		Properties props = new Properties();
		InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream(resource);
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	// 根据key获取val
	public static String getVal(String resource, String key) {
		Properties props = getResourceAsProperties(resource);
		return props.getProperty(key);
	}

	// 设置指定key->val
	public static void setVal(String resource, String key, String val) {
		Properties props = getResourceAsProperties(resource);
		props.setProperty(key, val);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(
					ConfigUtils.class.getClassLoader().getResource(resource).getPath().replace("%20", " "));
			props.store(fos, "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fos)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//
}
