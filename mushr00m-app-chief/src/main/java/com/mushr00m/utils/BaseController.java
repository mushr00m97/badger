package com.mushr00m.utils;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseController {
	// 自动绑定日期字段
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	// 输出指定string
	public void print(HttpServletResponse resp, String content) {
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
	public void printJson(HttpServletResponse resp, Object content) {
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
	public boolean isAjax(HttpServletRequest req) {
		return "XMLHttpRequest".equalsIgnoreCase(req.getHeader("X-Requested-With"));
	}

	// 设置application-val
	public void setAppVal(HttpServletRequest req, String name, Object val) {
		ServletContext app = req.getServletContext();
		app.setAttribute(name, val);
	}

	// 获取application-val
	public Object getAppVal(HttpServletRequest req, String name) {
		ServletContext app = req.getServletContext();
		Object obj = app.getAttribute(name);
		return null == obj ? "" : obj;
	}

	// 设置session-val
	public void setSessionVal(HttpServletRequest req, String name, Object val) {
		HttpSession ss = req.getSession();
		ss.setAttribute(name, val);
	}

	// 获取session-val
	public Object getSessionVal(HttpServletRequest req, String name) {
		HttpSession ss = req.getSession();
		Object obj = ss.getAttribute(name);
		return null == obj ? "" : obj;
	}

	// 设置cookie-val
	public void setCookieVal(HttpServletResponse resp, String name, String val) {
		if (val == null) {
			val = "";
		}
		try {
			Cookie cookie = new Cookie(name, URLEncoder.encode(val, "UTF-8"));
			cookie.setPath("/");
			resp.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// 获取cookie-val
	public String getCookieVal(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		if (cookies == null) {
			return "";
		}
		// cookies非空时
		try {
			for (Cookie ck : cookies) {
				if (name.equals(ck.getName()))
					return URLDecoder.decode(ck.getValue(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 获取基于application的缓冲队列
	public <T> LinkedList<T> getAppList(HttpServletRequest req, Class<T> clazz, String listName) {
		// 无list时建立list
		Object applist = this.getAppVal(req, listName);
		if (null == applist || "".equals(applist.toString().trim())) {
			LinkedList<T> list = new LinkedList<T>();
			this.setAppVal(req, listName, list);
		}
		// 返回list
		return (LinkedList<T>) this.getAppVal(req, listName);
	}

	// 向基于application的缓冲队列新增成员
	public <T> void saveToAppList(HttpServletRequest req, Class<T> clazz, String listName, T t, int maxCount) {
		LinkedList<T> applist = getAppList(req, clazz, listName);
		applist.add(t);
		while (applist.size() > maxCount)
			applist.poll();
		this.setAppVal(req, listName, applist);
	}

	// 获取基于session的缓冲队列
	public <T> LinkedList<T> getSessionList(HttpServletRequest req, Class<T> clazz, String listName) {
		// 无list时建立list
		Object sslist = this.getSessionVal(req, listName);
		if (null == sslist || "".equals(sslist.toString().trim())) {
			LinkedList<T> list = new LinkedList<T>();
			this.setSessionVal(req, listName, list);
		}
		// 返回list
		return (LinkedList<T>) this.getSessionVal(req, listName);
	}

	// 向基于session的缓冲队列新增成员
	public <T> void saveToSessionList(HttpServletRequest req, Class<T> clazz, String listName, T t, int maxCount) {
		LinkedList<T> sslist = getSessionList(req, clazz, listName);
		sslist.add(t);
		while (sslist.size() > maxCount)
			sslist.poll();
		this.setSessionVal(req, listName, sslist);
	}

	// 获取基于cookie的缓冲队列
	public <T> ArrayList<T> getCookieList(HttpServletRequest req, HttpServletResponse resp, Class<T> clazz,
			String listName) {
		// 无list时建立list
		String cklistStr = this.getCookieVal(req, listName);
		if (null == cklistStr || "".equals(cklistStr.trim())) {
			ArrayList<T> list = new ArrayList<T>();
			this.setCookieVal(resp, listName, JSON.toJSONString(list));
		}
		// 返回list
		String json = this.getCookieVal(req, listName);
		try {
			return (ArrayList<T>) JSONObject.parseArray(json, clazz);
		} catch (Exception e) {
			return new ArrayList<T>();
		}
	}
}

class DateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(text);
		} catch (ParseException e) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = format.parse(text);
			} catch (ParseException e1) {
				setValue(null);
			}
		}
		setValue(date);
	}
}
