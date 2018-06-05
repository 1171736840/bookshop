package com.pc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CompoundSelection;
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

import com.pc.entity.Address;
import com.pc.entity.User;

public class AddressDao {
	private static Log log = LogFactory.getLog(OrderDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addAddress(Address address) {//添加收货地址
		Session session = sessionFactory.getCurrentSession();
		session.save(address);
	}
	
	public Address getAddress(long addrNO) {//获取收货地址
		Session session = sessionFactory.getCurrentSession();
		return (Address)session.get(Address.class, addrNO);
	}
	
	public void deleteAddress(Address address) {//删除收货地址
		Session session = sessionFactory.getCurrentSession();
		session.delete(address);
	}
	public void updateAddress(Address address) {//更新收货地址
		Session session = sessionFactory.getCurrentSession();
		session.update(address);
	}
	
	public List<Address> getAllAddress(User user) {//获取所有收货地址
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();//创建一个条件查询者
		CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);//创建一个条件查询
		Root<Address> root = criteriaQuery.from(Address.class);//设置查询的表和对应的类
		//准备查询的字段，类中必须有个对应的构造方法，属性名称顺序要一致
		CompoundSelection<Address> cpdSelection = criteriaBuilder.construct(Address.class,root.get("addrNO"), root.get("receiver"), root.get("receivingAddress"), root.get("phone"), root.get("postcode"));
		criteriaQuery.select(cpdSelection);//设置查询的字段
		
		//准备查询条件
		List<Predicate> predicates = new ArrayList<Predicate>();//创建条件列表
		Predicate predicate = criteriaBuilder.equal(root.get("user"), user);//创建条件
		predicates.add(predicate);//添加到条件列表中
		Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);//转换成数组
		criteriaQuery.where(predicatesArray);//传入查询条件
		Query<Address> query = session.createQuery(criteriaQuery);//根据已有查询模板进行查询并返回结果集
		List<Address> addressList = query.getResultList();//读取查询结果
		return addressList;
		
	}
	
	
}
