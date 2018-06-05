package com.pc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pc.dao.AddressDao;
import com.pc.entity.Address;
import com.pc.entity.User;

@Controller
public class AddressController {
	@Autowired
	private AddressDao addressDao;//根据配置文件定义，运行时会自动获取memberDao的实列
	
	@ResponseBody//解析出json
	@RequestMapping(value="/address/addAddress",method=RequestMethod.POST)
	public Object addAddress(String receiver, String receivingAddress, String phone, String postcode, HttpSession session) {//添加收货地址
		//收货人，收货地址，电话，邮编
		Map<String, Object>json = new HashMap<>();
		try{
			User user = (User)session.getAttribute("user");		
			addressDao.addAddress(new Address(receiver, receivingAddress, phone, postcode, user));//添加收货地址信息
			json.put("message", "添加收货地址成功！");
			json.put("result", true);
		} catch (Exception e) {
			json.put("message", "添加收货地址失败！");
			json.put("result", false);
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/address/getAddress",method=RequestMethod.POST)
	public Object getAddress(long addrNO) {
		Map<String, Object>json = new HashMap<>();
		try {
			Address address = addressDao.getAddress(addrNO);
			if (address == null) {
				json.put("message", "收货地址不存在！");
				json.put("result", false);
			}else {
				json.put("address", address);
				json.put("message", "获取收货地址成功！");
				json.put("result", true);
			}
		} catch (Exception e) {
			json.put("message", "获取货地址失败！");
			json.put("result", false);
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/address/getAllAddress",method=RequestMethod.POST)
	public Object getAllAddress(HttpSession session) {
		Map<String, Object>json = new HashMap<>();
		try {
			User user = (User)session.getAttribute("user");
			List<Address> AddressList = addressDao.getAllAddress(user);//添加收货地址信息
			json.put("addressList", AddressList);
			
			json.put("message", "获取所有收货地址成功！");
			json.put("result", true);
		} catch (Exception e) {
			json.put("message", "获取所有收货地址失败！");
			json.put("result", false);
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/address/deleteAddress",method=RequestMethod.POST)
	public Object deleteAddress(String addrNO) {
		Map<String, Object>json = new HashMap<>();
		try {
			long addrNOLong = Long.parseLong(addrNO);
			System.out.println("---------------------------------------");
			System.out.println(addrNO);
			Address address = addressDao.getAddress(addrNOLong);
			if (address == null) {
				json.put("message", "收货地址不存在！");
				json.put("result", false);
			}else {
				addressDao.deleteAddress(address);
				json.put("message", "删除收货地址成功！");
				json.put("result", true);
			}
		} catch (Exception e) {
			json.put("message", "删除收货地址失败！");
			json.put("result", false);
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/address/updateAddress",method=RequestMethod.POST)
	public Object updateAddress(long addrNO, String receiver, String receivingAddress, String phone, String postcode) {
		Map<String, Object>json = new HashMap<>();
		try {
			Address address = addressDao.getAddress(addrNO);
			if (address == null) {
				json.put("message", "收货地址不存在！");
				json.put("result", false);
			}else {
				address.setReceiver(receiver);
				address.setReceivingAddress(receivingAddress);
				address.setPhone(phone);
				address.setPostcode(postcode);
				addressDao.updateAddress(address);
				json.put("message", "修改收货地址成功！");
				json.put("result", true);
			}
		} catch (Exception e) {
			json.put("message", "修改收货地址失败！");
			json.put("result", false);
		}
		return json;
	}
	
}
