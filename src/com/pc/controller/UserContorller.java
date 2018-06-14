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
import com.pc.dao.UserDao;
import com.pc.entity.User;
import com.pc.util.MD5;

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
	public Object regist(String logname, String password, String password2) {
		Map<String, Object>json = new HashMap<>();
//		System.out.println(logname.getBytes().length);
//		System.out.println(password .length());
		if(logname == null || "".equals(logname) || password == null || "".equals(password) || password2 == null || "".equals(password2)) {
			json.put("message", "注册失败，用户名或密码不能为空!");
		}else if(logname.length() > 10 || logname.length() < 2){
			json.put("message", "注册失败，用户名长度必须2-10个字符！");
		}else if(password.length() > 16 || password.length() < 8){
			json.put("message", "注册失败，密码长度必须8-16个字符！");
		}else if(!password.equals(password2)) {
			json.put("message", "注册失败，两次输入密码不一致！");
		}else if(userDao.getUser(logname) != null) {
			json.put("message", "注册失败，用户已存在!");
		}else {
			User user = new User(logname, MD5.getMd5(password));
			userDao.addUser(user);
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
				user = userDao.getUser(logname);
				if(user == null) {
					json.put("message", "登录失败，用户不存在！");
				}else {
					if(!user.getPassword().equals(MD5.getMd5(password))) {
						json.put("message", "登录失败，用户名或密码！");
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
	@RequestMapping(value="/modifyPassword")//修改密码
	public Object modifyPassword(HttpSession session, String oldPassword, String newPassword, String newPassword2) {
		Map<String, Object>json = new HashMap<>();
		
		try {
			User user = (User)session.getAttribute("user");
			
			if ("".equals(oldPassword)) {
				json.put("message", "老密码不能为空！");
				json.put("result", false);
			}else if ("".equals(newPassword)) {
				json.put("message", "新密码不能为空！");
				json.put("result", false);
			}else if ("".equals(newPassword2)) {
				json.put("message", "再次输入的新密码不能为空！");
				json.put("result", false);
			}else if (oldPassword.length() > 16 || oldPassword.length() < 8) {
				json.put("message", "老密码长度必须8-16个字符！");
				json.put("result", false);
			}else if (newPassword.length() > 16 || newPassword.length() < 8) {
				json.put("message", "新密码长度必须8-16个字符！");
				json.put("result", false);
			}else if (newPassword2.length() > 16 || newPassword2.length() < 8) {
				json.put("message", "再次输入的新密码长度必须8-16个字符！");
				json.put("result", false);
			}else if (oldPassword.equals(newPassword)) {
				json.put("message", "新老密码不能相同！");
				json.put("result", false);
			}else if (!newPassword2.equals(newPassword)) {
				json.put("message", "两次输入的新密码不相同！");
				json.put("result", false);
			}else {
				user.setPassword(MD5.getMd5(newPassword));
				userDao.update(user);
				logout(session);
				json.put("message", "修改密码成功！");
				json.put("result", true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "修改密码失败！");
			json.put("result", false);
		}
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
