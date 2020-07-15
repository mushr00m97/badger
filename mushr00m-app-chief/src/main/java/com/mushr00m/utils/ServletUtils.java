package com.mushr00m.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;

public class ServletUtils {
	// 输出指定string
	public static void print(HttpServletResponse resp, String content) {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.print(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 输出json化的实体/实体集合/map/map集合
	public static void printJson(HttpServletResponse resp, Object content) {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.print(JSON.toJSONString(content));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 判断是否ajax请求
	public static boolean isAjax(HttpServletRequest req) {
		return "XMLHttpRequest".equalsIgnoreCase(req.getHeader("X-Requested-With"));
	}

	// 转发或输出
	public static void showMsg(HttpServletRequest req, HttpServletResponse resp, String msgUrl, String msg) {
		if (isAjax(req)) {
			print(resp, msg);
		} else {
			try {
				req.getRequestDispatcher(msgUrl).forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}