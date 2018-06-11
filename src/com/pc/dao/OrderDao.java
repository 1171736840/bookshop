package com.pc.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import com.pc.entity.Order;
import com.pc.entity.OrderItem;
import com.pc.entity.User;

public class OrderDao {
	private static Log log = LogFactory.getLog(OrderDao.class);
	@Autowired
	private SessionFactory sessionFactory;

	public void addOrder(Order order) {
		Session session = sessionFactory.getCurrentSession();
		session.save(order);
	}
	
	public Order getOrder(int orderNO) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Order.class, orderNO);
	}
	public void updateOrder(Order order) {
		Session session = sessionFactory.getCurrentSession();
		session.update(order);
	}
	public OrderItem getOrderItem(long orderItemNO) {
		System.out.println(orderItemNO);
		Session session = sessionFactory.getCurrentSession();
		return session.get(OrderItem.class, orderItemNO);
	}
	public void updateOrderItem(OrderItem OrderItem) {
		Session session = sessionFactory.getCurrentSession();
		session.update(OrderItem);
	}

	public List<Order> getAllOrder(User user) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();//创建一个条件查询者
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);//创建一个条件查询
		Root<Order> root = criteriaQuery.from(Order.class);//设置查询的表和对应的类	
		//准备查询条件
		List<Predicate> predicates = new ArrayList<Predicate>();//创建条件列表
		Predicate predicate = criteriaBuilder.equal(root.get("logname"), user.getLogname());//创建条件
		predicates.add(predicate);//添加到条件列表中
		Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);//转换成数组
		criteriaQuery.where(predicatesArray);//传入查询条件
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderDate")));	//倒序排列
		Query<Order> query = session.createQuery(criteriaQuery);//根据已有查询模板进行查询并返回结果集
		List<Order> orderList = query.getResultList();//读取查询结果	
		return orderList;
	}
}
