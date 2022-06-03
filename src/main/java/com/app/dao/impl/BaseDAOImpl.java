package com.app.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.BaseDAO;
import com.app.dto.Paging;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {
	@Autowired
	SessionFactory factory;
	
	public E findById(Class<E> instance, Serializable id) {
		// TODO Auto-generated method stub
 		return factory.getCurrentSession().find(instance, id);
	}

	public List<E> findAll(Map<String, Object> mapParams, String queryStr, Paging paging) {
		// TODO Auto-generated method stub
		StringBuilder queryBuild = new StringBuilder();
		StringBuilder queryCount = new StringBuilder();
		queryBuild.append("FROM ").append(getGenericName()).append(" as model where model.activeFlag = 1 ");
		queryCount.append("SELECT COUNT(*) FROM ").append(getGenericName()).append(" as model where model.activeFlag = 1 ");
		if(queryStr != null && mapParams != null) {
			queryBuild.append(queryStr);
			queryCount.append(queryStr);
		}
		System.out.println(queryBuild);
		System.out.println(queryCount);
		Query<E> queryList = factory.getCurrentSession().createQuery(queryBuild.toString());
		Query<E> queryNumber = factory.getCurrentSession().createQuery(queryCount.toString());
		if(mapParams != null ) {
			for(String key : mapParams.keySet()) {
				queryList.setParameter(key, mapParams.get(key));
				queryNumber.setParameter(key, mapParams.get(key));
			}
		}
		if(paging != null) {
			long count = (Long) queryNumber.uniqueResult();
			paging.setTotalProduct(count);
			queryList.setFirstResult(paging.getOffSet());
			queryList.setMaxResults(paging.getNumberPerPage());
		}
		return queryList.getResultList();
	}

	public List<E> findByProperty(String property, Object object) {
		StringBuilder queryBuild = new StringBuilder();
		queryBuild.append("FROM ").append(getGenericName()).append(" where activeFlag = 1 ")
		.append(" and ").append(property);
		property = property.replace(".", "");
		queryBuild.append(" = :").append(property);
		System.out.println(queryBuild.toString());
		Query<E> queryList = factory.getCurrentSession().createQuery(queryBuild.toString());
		queryList.setParameter(property, object);
		return queryList.getResultList();
	}

	public void insert(E e) {
		// TODO Auto-generated method stub
		System.out.println("-------------insert---------------");
		factory.getCurrentSession().persist(e);
	}
	
	public Long insertAndReturn(E e) {
		// TODO Auto-generated method stub
		return (Long) factory.getCurrentSession().save(e);
	}

	public void update(E e) {
		// TODO Auto-generated method stub
		System.out.println("-------------update---------------");
		factory.getCurrentSession().merge(e);
	}

	public void delete(E e) {
		// TODO Auto-generated method stub
		System.out.println("-------------delete---------------");
		factory.getCurrentSession().merge(e);
	}

	public String getGenericName() { // Trả về class hiện tại đag sử dụng
		String s = getClass().getGenericSuperclass().toString();
		Pattern pattern = Pattern.compile("\\<(.*?)\\>"); // tạo pattern
		Matcher m = pattern.matcher(s); // check xem s có hợp lệ hay k , trả về Matcher
		String generic="null"; 
		if(m.find()) { // kiểm tra 
			generic = m.group(1);
		}
		return generic;
	}
 
}
