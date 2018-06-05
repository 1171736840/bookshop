package com.pc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mchange.v1.lang.NullUtils;
import com.pc.dao.UserDao;
import com.pc.entity.User;

@Controller
public class UserContorller {
	private static Log log = LogFactory.getLog(UserContorller.class);//加载日志记录
	@Autowired
	private UserDao userDao;//根据配置文件定义，运行时会自动获取memberDao的实列
	
	@RequestMapping(value="/jsp/{pageName}")
	public String getJspPage(@PathVariable("pageName")String pageName, HttpServletRequest request) {
		return pageName;
		
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public Object regist(String logname, String password) {
		Map<String, Object>json = new HashMap<>();
//		System.out.println(logname.getBytes().length);
//		System.out.println(password .length());
		if(logname == null || "".equals(logname) || password == null || "".equals(password)) {
			json.put("message", "注册失败，用户名或密码不能为空!");
		}else if(logname.length() > 10){
			json.put("message", "注册失败，用户名过长!");
		}else if(password.length() > 10){
			json.put("message", "注册失败，密码过长!");
		}else if(userDao.getUser(logname) != null) {
			json.put("message", "注册失败，用户已存在!");
		}else {
			userDao.addUser(logname, password);
			if(userDao.getUser(logname) == null) {
				json.put("message", "注册失败，请稍后重试！");
			}else {
				json.put("message", "注册成功！");
				json.put("result", true);
			}
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Object login(String logname, String password, HttpSession session, HttpServletRequest request) {
		Map<String, Object>json = new HashMap<>();
//		System.out.println(logname.getBytes().length);
//		System.out.println(password .length());
		
		if((User)session.getAttribute("user") != null) {
			json.put("message", "登录失败，请先退出当前登录用户!");
		}else if(logname == null || "".equals(logname) || password == null || "".equals(password)) {
			json.put("message", "登录失败，用户名或密码不能为空!");
		}else {
			User user;
			try {
				user = userDao.getUser(logname,password);
				if(user == null) {
					json.put("message", "登录失败，用户不存在！");
				}else {
					session.setAttribute("user", user);
					json.put("message", "登录成功！");
					Map<String, String> u = new HashMap<>();
					u.put("logname", user.getLogname());
					u.put("photo", user.getPhoto());
					json.put("user", u);
					json.put("photoSrc", request.getContextPath() + "/img/photo/");
					json.put("result", true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("message", "登录失败，数据库错误！");
			}

		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/logout")//退出登录
	public Object logout(HttpSession session) {
		Map<String, Object>json = new HashMap<>();
		session.invalidate();
		json.put("message", "退出登录成功！");
		json.put("result", true);
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/notLogin")//用于过滤器跳转，显示用户未登录
	public Object notLogin(HttpSession session) {
		Map<String, Object>json = new HashMap<>();
		json.put("message", "您尚未登录，请登录后重试！");
		json.put("result", false);
		json.put("code", 100);//表示用户未登录
		System.out.println("过滤器拦截，用户未登录");
		return json;
	}
}
