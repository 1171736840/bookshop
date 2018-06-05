package com.pc.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pc.dao.BookfromDao;
import com.pc.entity.Book;
import com.pc.entity.BookComment;

@Controller
public class BookController {
	private static Log log = LogFactory.getLog(BookController.class);//加载日志记录
	@Autowired
	private BookfromDao bookfromDao;//根据配置文件定义，运行时会自动获取bookfromDao的实列
	
	
	@ResponseBody//解析出json
	@RequestMapping(value="/showAllBook", method=RequestMethod.POST)
	public Object showAllBook(HttpServletRequest request, String bookName, String bookAuthor, String bookPublish, String thisPageNumber) {
		Map<String, Object>json = new HashMap<>();
		int thisPageNumberInt = 0;
		try {
			thisPageNumberInt = Integer.parseInt(thisPageNumber);
		} catch (Exception e) {}
		System.out.println("当前页数" + thisPageNumberInt);
		json.put("message", "执行成功！");
		json.put("result", true);
		json.putAll(bookfromDao.selectBook(bookName,bookAuthor,bookPublish,thisPageNumberInt));
		json.put("bookPicSrc", request.getContextPath() + "/img/bookPic/");
		
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/showBook", method=RequestMethod.POST)
	public Object showBook(String bookISBN, HttpServletRequest request) {
		Map<String, Object>json = new HashMap<>();
//		System.out.println("bookISBN" + bookISBN);
		json.put("message", "执行成功！");
		json.put("result", true);
		Book book = bookfromDao.getBook(bookISBN);
		json.put("book", book);
		json.put("bookPicSrc", request.getContextPath() + "/img/bookPic/");
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/showBookComment", method=RequestMethod.POST)
	public Object showBookComment(String bookISBN, HttpServletRequest request, String thisPageNumber) {
		Map<String, Object>json = new HashMap<>();
//		System.out.println("bookISBN" + bookISBN);
		json.put("message", "执行成功！");
		json.put("result", true);
		int thisPageNumberInt = 0;
		try {
			thisPageNumberInt = Integer.parseInt(thisPageNumber);
		} catch (Exception e) {}
		json.putAll(bookfromDao.getBookComment(bookISBN, thisPageNumberInt));
		return json;
	}
	
	@ResponseBody//解析出json
	@RequestMapping(value="/deleteBook", method=RequestMethod.POST)
	public Object deleteBook(String bookISBN, HttpServletRequest request) {
		Map<String, Object>json = new HashMap<>();

		Book book = bookfromDao.getBook(bookISBN);
		if(book != null) {
			try {
				bookfromDao.deleteBook(book);
				String bookPic = request.getServletContext().getRealPath("/img/bookPic/");//图片存放路径
				bookPic += book.getBookISBN() + book.getBookPic();
				File bookPicFile = new File(bookPic);
				bookPicFile.delete();
				json.put("message", "删除图书成功！");
				json.put("result", false);
			} catch (Exception e) {
				json.put("message", "删除图书失败！");
				json.put("result", false);
			}
		}else {
			json.put("message", "图书不存在！");
			json.put("result", false);
		}
		
		return json;
	}
	
	
}
