package com.pc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShoppingCartController {
	private static Log log = LogFactory.getLog(UserContorller.class);//加载日志记录
	
	@ResponseBody//解析出json
	@RequestMapping(value="/addShoppingCart",method=RequestMethod.POST)
	public Object addShoppingCart(String bookISBN, int number, HttpSession session) {//将商品加入到购物车中
		Map<String, Object>json = new HashMap<>();
		
		try {
			ArrayList<HashMap<String, Object>> shoppingCart = (ArrayList<HashMap<String, Object>>)session.getAttribute("shoppingCart");
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
				session.setAttribute("shoppingCart", shoppingCart);
			}
			//创建订单
			HashMap<String, Object> order = new HashMap<>();
			order.put("id", new Date().getTime());
			order.put("bookISBN", bookISBN);
			order.put("number", number);
			order.put("date", new Date());
			shoppingCart.add(order);
			
			json.put("message", "添加购物车成功！");
			json.put("result", true);
		} catch (Exception e) {
			json.put("message", "添加购物车失败！");
			json.put("result", false);
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/getAllShoppingCart",method=RequestMethod.POST)
	public Object getAllShoppingCart(HttpSession session) {//获取购物车所有数据
		Map<String, Object>json = new HashMap<>();
		json.put("shoppingCart", session.getAttribute("shoppingCart"));
		json.put("result", true);
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/deleteShoppingCart",method=RequestMethod.POST)
	public Object deleteShoppingCart(long id, HttpSession session) {//获取购物车所有数据
		Map<String, Object>json = new HashMap<>();
		try {
			ArrayList<HashMap<String, Object>> shoppingCart = (ArrayList<HashMap<String, Object>>)session.getAttribute("shoppingCart");
			
			ArrayList<HashMap<String, Object>> newShoppingCart = new ArrayList<>();
			for(HashMap<String, Object>shopping : shoppingCart) {
				if((long)shopping.get("id") != id) {
					newShoppingCart.add(shopping);
				}
			}
			session.setAttribute("shoppingCart", newShoppingCart);
			json.put("result", true);
			json.put("message", "删除商品成功！");
		} catch (Exception e) {
			json.put("result", false);
			json.put("message", "删除商品失败！");
		}
		
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/setShoppingCartNumber",method=RequestMethod.POST)
	public Object setShoppingCartNumber(long id, int number, HttpSession session) {//获取购物车所有数据
		Map<String, Object>json = new HashMap<>();
		try {
			ArrayList<HashMap<String, Object>> shoppingCart = (ArrayList<HashMap<String, Object>>)session.getAttribute("shoppingCart");

			for(HashMap<String, Object>shopping : shoppingCart) {
				if((long)shopping.get("id") == id) {
					shopping.put("number", number);
				}
			}
			json.put("id", id);
			json.put("number", number);
			json.put("result", true);
			json.put("message", "修改商品数量成功！");
		} catch (Exception e) {
			json.put("result", false);
			json.put("message", "修改商品数量失败！");
		}
		
		return json;
	}
	

}
