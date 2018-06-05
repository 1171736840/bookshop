package com.pc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import com.pc.entity.Book;
import com.pc.entity.BookComment;
import com.pc.util.ProjectCfg;

public class BookfromDao {
	private static Log log = LogFactory.getLog(BookfromDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Book getBook(String bookISBN) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Book.class, bookISBN);
	}
	public Map<String, Object> getBookComment(String bookISBN, int thisPageNumber) {
		Session session = sessionFactory.getCurrentSession();
		Map<String, Object> rst = new HashMap<>();
		//查询图书评论总数
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();//创建一个条件查询者
		CriteriaQuery<Object> countQuery = criteriaBuilder.createQuery();//根据条件查询创建一个计数查询
		Root<BookComment> root = countQuery.from(BookComment.class);//设置查询的表和对应的类
		countQuery.select(criteriaBuilder.count(root));//拼接sql查询语句
		//准备查询条件
		List<Predicate> predicates = new ArrayList<Predicate>();//创建条件列表
		
		if (bookISBN != null && ! "".equals(bookISBN)) {
			Book book = session.get(Book.class, bookISBN);//根据ISBN查询到图书
			Predicate predicate = criteriaBuilder.equal(root.get("book"), book);//创建条件
			predicates.add(predicate);//添加到条件列表中
		}
		
		Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);//转换成数组
		countQuery.where(predicatesArray);//传入查询条件
		
		Query<Object> itemsQuery = session.createQuery(countQuery);//根据已有查询模板进行查询并返回结果集
		
		long resultNumber = (long)itemsQuery.getSingleResult();
		rst.put("resultNumber", resultNumber);
//		System.out.println("记录总数：" + resultNumber);
		
		int pageSize = ProjectCfg.getBookCommentPageSize();//从配置文件中获取页面显示记录数量
		long maxPageNumber = resultNumber / pageSize;//获取总页数
		if(resultNumber % pageSize != 0) {
			maxPageNumber ++;
		}
		
		thisPageNumber = thisPageNumber < 1 ? 1 : thisPageNumber;
		thisPageNumber = thisPageNumber > maxPageNumber ? 1 : thisPageNumber;
		
		int start = (thisPageNumber - 1) * pageSize;//计算分页开始读取记录位置
		
		rst.put("maxPageNumber", maxPageNumber);
		rst.put("thisPageNumber", thisPageNumber);
		
		//查询图书内容
		
		CriteriaQuery<BookComment> criteriaQuery = criteriaBuilder.createQuery(BookComment.class);//创建一个条件查询
		criteriaQuery.from(BookComment.class);//绑定查询的表或类
		//准备查询的字段，类中必须有个对应的构造方法，属性名称顺序要一致
		CompoundSelection<BookComment> cpdSelection = criteriaBuilder.construct(BookComment.class, root.get("description"), root.get("logname"), root.get("commentDate"));
		criteriaQuery.select(cpdSelection);//设置查询的字段
		criteriaQuery.where(predicatesArray);//传入查询条件
		
		Query<BookComment> query = session.createQuery(criteriaQuery);//根据已有查询模板进行查询并返回结果集
		query.setFirstResult(start);//设置记录开始读取位置
		query.setMaxResults(pageSize);//设置读取行数
		
		List<BookComment> bookCommentList = query.getResultList();//读取查询结果
		
		rst.put("bookCommentList", bookCommentList);
		
		return rst;
	}
	
	public Map<String, Object> selectBook(String bookName, String bookAuthor, String bookPublish, int thisPageNumber) {
		Session session = sessionFactory.getCurrentSession();
		Map<String, Object> rst = new HashMap<>();
		
		//查询图书总数
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();//创建一个条件查询者
		CriteriaQuery<Object> countQuery = criteriaBuilder.createQuery();//根据条件查询创建一个计数查询
		Root<Book> root = countQuery.from(Book.class);//设置查询的表和对应的类
		countQuery.select(criteriaBuilder.count(root));//拼接sql查询语句
		
		//准备查询条件
		List<Predicate> predicates = new ArrayList<Predicate>();//创建条件列表
		
		if (bookName != null && ! "".equals(bookName)) {
			Predicate predicate = criteriaBuilder.like(root.get("bookName"), "%" + bookName + "%");//创建条件
			predicates.add(predicate);//添加到条件列表中
		}
		if (bookAuthor != null && ! "".equals(bookAuthor)) {
			Predicate predicate = criteriaBuilder.like(root.get("bookAuthor"), "%" + bookAuthor + "%");//创建条件
			predicates.add(predicate);//添加到条件列表中
		}
		if (bookPublish != null && ! "".equals(bookPublish)) {
			Predicate predicate = criteriaBuilder.like(root.get("bookPublish"), "%" + bookPublish + "%");//创建条件
			predicates.add(predicate);//添加到条件列表中
		}
		Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);//转换成数组
		countQuery.where(predicatesArray);//传入查询条件
		
		
		Query<Object> itemsQuery = session.createQuery(countQuery);//根据已有查询模板进行查询并返回结果集
		
		long resultNumber = (long)itemsQuery.getSingleResult();
		rst.put("resultNumber", resultNumber);
//		System.out.println("记录总数：" + resultNumber);
		
		
		int pageSize = ProjectCfg.getPageSize();//从配置文件中获取页面显示记录数量
		long maxPageNumber = resultNumber / pageSize;//获取总页数
		if(resultNumber % pageSize != 0) {
			maxPageNumber ++;
		}
		
		thisPageNumber = thisPageNumber < 1 ? 1 : thisPageNumber;
		thisPageNumber = thisPageNumber > maxPageNumber ? 1 : thisPageNumber;
		
		int start = (thisPageNumber - 1) * pageSize;//计算分页开始读取记录位置
		
		rst.put("maxPageNumber", maxPageNumber);
		rst.put("thisPageNumber", thisPageNumber);
		
		//查询图书内容
		
		CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);//创建一个条件查询
		criteriaQuery.from(Book.class);//绑定查询的表或类
		//准备查询的字段，类中必须有个对应的构造方法，属性名称顺序要一致
		CompoundSelection<Book> cpdSelection = criteriaBuilder.construct(Book.class, root.get("bookISBN"), root.get("bookPic"), root.get("bookName"), root.get("bookAuthor"), root.get("bookPrice"), root.get("bookPublish"));
		criteriaQuery.select(cpdSelection);//设置查询的字段
		criteriaQuery.where(predicatesArray);//传入查询条件
		
		Query<Book> query = session.createQuery(criteriaQuery);//根据已有查询模板进行查询并返回结果集
		query.setFirstResult(start);//设置记录开始读取位置
		query.setMaxResults(pageSize);//设置读取行数
		
		List<Book> bookList = query.getResultList();//读取查询结果
		
		rst.put("bookList", bookList);
		return rst;
	}
	public void deleteBook(Book book) {//删除图书
		Session session = sessionFactory.getCurrentSession();
		session.delete(book);
	}
}
