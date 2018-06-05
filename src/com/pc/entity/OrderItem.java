package com.pc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value= {"order","hibernateLazyInitializer","handler"})	//json转换时忽略这些成员,后面两个是bibernate处理懒加载是自动添加的
public class OrderItem {
	private long itemNO;
	private Order order;
	private Book book;
	private float bookPrice;
	private int count;
	private boolean commentStatus;
	
	public OrderItem() {
		// TODO Auto-generated constructor stub
	}
	
	public long getItemNO() {
		return itemNO;
	}
	
	public void setItemNO(long itemNO) {
		this.itemNO = itemNO;
	}
	
	public Order getOrder() {
		return order;
	}
	@JsonProperty
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public float getBookPrice() {
		return bookPrice;
	}
	
	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public boolean isCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(boolean commentStatus) {
		this.commentStatus = commentStatus;
	}
	
}
