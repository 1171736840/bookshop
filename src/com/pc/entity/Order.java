package com.pc.entity;

import java.util.Date;
import java.util.List;

public class Order {
	private int orderNO;
	private String logname;
	private Date orderDate;
	private float sum;
	private String orderStatus;
	private String receiver;
	private String address;
	private String phone;
	private String postcode;
	private List<OrderItem> orderItemList;
	
	public Order() {
		// TODO Auto-generated constructor stub
	}
	
	public int getOrderNO() {
		return orderNO;
	}
	
	public void setOrderNO(int orderNO) {
		this.orderNO = orderNO;
	}
	
	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public float getSum() {
		return sum;
	}
	
	public void setSum(float sum) {
		this.sum = sum;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
}
