package com.pc.controller;

import java.util.Date;
import java.util.ArrayList;
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
import com.pc.dao.BookfromDao;
import com.pc.dao.OrderDao;
import com.pc.entity.Address;
import com.pc.entity.Book;
import com.pc.entity.BookComment;
import com.pc.entity.Order;
import com.pc.entity.OrderItem;
import com.pc.entity.User;

@Controller
public class OrderController {
	@Autowired
	private OrderDao orderDao;//根据配置文件定义，运行时会自动获取orderDao的实列
	@Autowired
	private AddressDao addressDao;//根据配置文件定义，运行时会自动获取memberDao的实列
	@Autowired
	private BookfromDao bookfromDao;//根据配置文件定义，运行时会自动获取bookfromDao的实列
	
	@ResponseBody//解析出json
	@RequestMapping(value="/order/createOrder",method=RequestMethod.POST)
	public Object createOrder(String orderIds, String addressId, HttpSession session) {//创建订单
		Map<String, Object>json = new HashMap<>();
		try {
			//查找收货地址
			Address address = addressDao.getAddress(Long.parseLong(addressId));//读取收货地址
			if(address == null) {
				json.put("result", false);
				json.put("message", "订单创建失败,找不到收货地址！");
			}else {
				String[] orderIdArray = orderIds.split(",");
				ArrayList<HashMap<String, Object>> shoppingCart = (ArrayList<HashMap<String, Object>>)session.getAttribute("shoppingCart");
				ArrayList<HashMap<String, Object>> shoppingCartTemp = new ArrayList<>();
				ArrayList<HashMap<String, Object>> settlementCommodityList = new ArrayList<>();//结算商品列表
				for(HashMap<String, Object> commodity : shoppingCart) {
					boolean flag = false;
					for(String id : orderIdArray) {
						if(id.equals(commodity.get("id").toString())) {
							settlementCommodityList.add(commodity);
							flag = true;
						}
					}
					if (! flag) {
						shoppingCartTemp.add(commodity);
					}
				}
				
				if(settlementCommodityList.size() == 0) {
					json.put("result", false);
					json.put("message", "订单创建失败,未选择欲结算的商品！");
				}else {
					User user = (User)session.getAttribute("user");
					Order order = new Order();
					order.setLogname(user.getLogname());
					order.setOrderDate(new Date());	//获取当前时间
					order.setOrderStatus("未付款");//订单状态
					order.setPhone(address.getPhone());//电话
					order.setPostcode(address.getPostcode());//邮编
					order.setReceiver(address.getReceiver());//收货人
					order.setAddress(address.getReceivingAddress());//收货地址
					float sum = 0;//总价
					List<OrderItem> orderItemList = new ArrayList<>();
					for(HashMap<String, Object> commodity : settlementCommodityList) {//遍历购物车中的商品，创建出相应的订单并计算总价格
						OrderItem orderItem = new OrderItem();
						Book book = bookfromDao.getBook((String)commodity.get("bookISBN"));
						orderItem.setBook(book);//书本
						orderItem.setBookPrice(book.getBookPrice());//书本价格
						orderItem.setCommentStatus(false);//书本评论状态
						orderItem.setCount((int)commodity.get("number"));
						orderItemList.add(orderItem);
						sum += orderItem.getCount() * book.getBookPrice();
					}
					order.setOrderItemList(orderItemList);
					order.setSum(sum);//总价
					orderDao.addOrder(order);//保存订单
					session.setAttribute("shoppingCart", shoppingCartTemp);//保存购物车
					json.put("result", true);
					json.put("message", "订单创建成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", false);
			json.put("message", "订单创建失败！");
		}
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/order/getAllOrder",method=RequestMethod.POST)
	public Object getAllOrder(String orderIds, String addressId, HttpSession session) {//获取所有订单
		Map<String, Object>json = new HashMap<>();
		User user = (User) session.getAttribute("user");
		List<Order> orderList = orderDao.getAllOrder(user);
		json.put("orderList", orderList);
		json.put("result", true);
		json.put("message", "获取所有订单成功！");
		return json;
	}
	@ResponseBody//解析出json
	@RequestMapping(value="/order/settlementOrder",method=RequestMethod.POST)
	public Object settlementOrder(String orderNO, HttpSession session) {//结算订单
		Map<String, Object>json = new HashMap<>();
		try {
			Order order = orderDao.getOrder(Integer.parseInt(orderNO));
			order.setOrderStatus("已结算");
			orderDao.updateOrder(order);
			json.put("result", true);
			json.put("message", "结算订单成功！");
		} catch (Exception e) {
			json.put("result", false);
			json.put("message", "结算订单失败！");
		}
		return json;
	}

	@ResponseBody//解析出json
	@RequestMapping(value="/order/submitBookComment",method=RequestMethod.POST)
	public Object submitBookComment(String orderItemNO, String comment, HttpSession session) {//结算订单
		Map<String, Object>json = new HashMap<>();
		try {
			OrderItem orderItem = orderDao.getOrderItem(Long.parseLong(orderItemNO));
			orderItem.setCommentStatus(true);

			User user = (User) session.getAttribute("user");
			BookComment bookComment = new BookComment();
			bookComment.setBook(orderItem.getBook());
			bookComment.setCommentDate(new Date());
			bookComment.setDescription(comment);
			bookComment.setLogname(user.getLogname());
			bookComment.setOrderItem(orderItem);
			
			orderDao.updateOrderItem(orderItem);
			bookfromDao.addBookComment(bookComment);
			
			json.put("result", true);
			json.put("message", "评价成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", false);
			json.put("message", "评价失败！");
		}
		return json;
	}
	@ResponseBody//解析出json
	@RequestMapping(value="/order/getBookComment",method=RequestMethod.POST)
	public Object getBookComment(String orderItemNO) {//结算订单
		Map<String, Object>json = new HashMap<>();
		try {
			json.put("bookComment", bookfromDao.getBookComment(Long.parseLong(orderItemNO)).getDescription());
			json.put("result", true);
			json.put("message", "获取评价成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", false);
			json.put("message", "获取评价失败！");
		}
		return json;
	}
}
