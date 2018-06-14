package com.pc.entity;

import java.util.List;

public class User {
	private String logname;
	private String password;
	private String photo;
	private List<Address> addressList;
	
	public User() {
		
	}
	public User(String logname, String password) {
		this.logname = logname;
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getLogname() {
		return logname;
	}
	
	public void setLogname(String logname) {
		this.logname = logname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	
}
