package com.pc.entity;

import java.util.Date;

public class BookComment {
	private long commentNO;
	private Book book;
	private String logname;
	private String description;
	private Date commentDate;
	
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
