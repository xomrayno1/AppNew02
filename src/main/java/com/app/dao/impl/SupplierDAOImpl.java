package com.app.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.SupplierDAO;
import com.app.entity.Supplier;
@Repository
@Transactional(rollbackFor = Exception.class)
public class SupplierDAOImpl  extends BaseDAOImpl<Supplier> implements SupplierDAO<Supplier>{

	 
	public Supplier findByName(String name) {
		// TODO Auto-generated method stub
		Query query = 	factory.getCurrentSession()
				.createQuery(" FROM Supplier as model where model.activeFlag = 1 and model.name = :name ");
		query.setParameter("name", name);
		if(query.getSingleResult() == null) {
			return null;
		}else {
			return (Supplier) query.getSingleResult();
		}
	}

}
