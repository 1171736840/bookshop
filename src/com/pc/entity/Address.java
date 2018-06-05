package com.pc.entity;

public class Address {
	private long addrNO;
	private User user;
	private String receiver;
	private String receivingAddress;
	private String phone;
	private String postcode;
	
	public Address() {
		// TODO Auto-generated constructor stub
	}
	
	public Address(long addrNO, String receiver, String receivingAddress, String phone, String postcode) {
		this.addrNO = addrNO;
		this.receiver = receiver;
		this.receivingAddress = receivingAddress;
		this.phone = phone;
		this.postcode = postcode;
	}
	
	public Address(String receiver, String receivingAddress, String phone, String postcode, User user) {
		this.receiver = receiver;
		this.receivingAddress = receivingAddress;
		this.phone = phone;
		this.postcode = postcode;
		this.user = user;
	}

	public long getAddrNO() {
		return addrNO;
	}
	
	public void setAddrNO(long addrNO) {
		this.addrNO = addrNO;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
	
	public String getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
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
	
}
