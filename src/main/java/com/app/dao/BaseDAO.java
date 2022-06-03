package com.app.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.app.dto.Paging;

public interface BaseDAO<E> {
	E findById(Class<E> instance,Serializable id);
	List<E> findAll(Map<String, Object> mapParams , String queryStr , Paging paging );
	List<E> findByProperty(String property , Object object);
	void insert(E e);
	void update(E e);
	void delete(E e);
	Long insertAndReturn(E e);
	 
	 
}
