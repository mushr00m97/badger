package com.mushr00m.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileUtils {

	public final static int FILE_SKIPEXIST = 0;
	public final static int FILE_OVERWRITE = 1;

	// 获取工程src路径
	public static String getCurrentSrcPath() {
		String path = new File("").getAbsolutePath() + "\\src\\main\\java\\";
		return path;
	}

	// 获取工程classpath路径
	public static String getCurrentClassPath() {
		String path = MyFileUtils.class.getClassLoader().getResource("").getPath().substring(1).replace("%20", " ")
				.replace("/", "\\");
		return path;
	}

	// 在指定路径生成文件/并写入字符串
	public static void writeFile(String target, String fileName, String content, int mode) {
		// mode=0时/不生成已存在的文件
		if (mode == 0) {
			if (fileExist(target + "\\" + fileName))
				return;
		}
		// mode=1时/覆盖已存在的文件
		FileWriter wt = null;
		try {
			wt = new FileWriter(target + "\\" + fileName);
			wt.write(content);
			wt.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wt.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 拷贝文件到指定路径/保持文件名
	public static void copyFileKeepName(String source, String target, int mode) {
		// 获取文件名
		File file = new File(source);
		String name = file.getName();

		// mode=0时/不拷贝已存在的文件
		if (mode == 0) {
			if (fileExist(target + "\\" + name))
				return;
		}
		// mode=1时/覆盖已存在的文件
		FileInputStream instrm = null;
		FileOutputStream outstrm = null;
		try {
			instrm = new FileInputStream(source);
			outstrm = new FileOutputStream(target + "\\" + name);
			byte[] buf = new byte[1024];
			int len;
			while ((len = instrm.read(buf)) != -1) {
				outstrm.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outstrm.close();
				instrm.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 拷贝文件到指定路径/设置文件名
	public static void copyFileSetName(String source, String target, String newFileName, int mode) {
		// mode=0时/不拷贝已存在的文件
		if (mode == 0) {
			if (fileExist(target + "\\" + newFileName))
				return;
		}
		// mode=1时/覆盖已存在的文件
		FileInputStream instrm = null;
		FileOutputStream outstrm = null;
		try {
			instrm = new FileInputStream(source);
			outstrm = new FileOutputStream(target + "\\" + newFileName);
			byte[] buf = new byte[1024];
			int len;
			while ((len = instrm.read(buf)) != -1) {
				outstrm.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outstrm.close();
				instrm.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 判断文件是否存在
	public static boolean fileExist(String target) {
		File file = new File(target);
		return file.exists();
	}

	// 删除指定文件
	public static void deleteFile(String target) {
		File file = new File(target);
		file.delete();
	}
}