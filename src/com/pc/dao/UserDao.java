package com.pc.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.pc.entity.User;

public class UserDao {
	private static Log log = LogFactory.getLog(UserDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addUser(User user) {//向数据库中添加用户
		log.info("注册账号：" + user.getLogname() + "密码：" + user.getPassword());
		sessionFactory.getCurrentSession().save(user);
	}
	public User getUser(String logname) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, logname);
		return user;
	}
	public User getUser(String logname, String password) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from User where logname=:logname and password=:password");
		query.setParameter("logname", logname);
		query.setParameter("password", password);
		return query.uniqueResult();
	}
	public void update(User user) {//更新用户
		sessionFactory.getCurrentSession().update(user);
	}
}
