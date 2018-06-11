package com.pc.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value= {"order","hibernateLazyInitializer","handler"})	//json转换时忽略这些成员,后面两个是bibernate处理懒加载是自动添加的
public class BookComment {
	private long commentNO;
	private Book book;
	private String logname;
	private String description;
	private Date commentDate;
	private OrderItem orderItem;
	
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	@JsonProperty
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public BookComment(String description, String logname, Date commentDate) {
		this.description = description;
		this.logname = logname;
		this.commentDate = commentDate;
	}
	public BookComment() {
		// TODO Auto-generated constructor stub
	}
	
	public long getCommentNO() {
		return commentNO;
	}
	
	public void setCommentNO(long commentNO) {
		this.commentNO = commentNO;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public String getLogname() {
		return logname;
	}
	
	public void setLogname(String logname) {
		this.logname = logname;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCommentDate() {
		return commentDate;
	}
	
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
}
