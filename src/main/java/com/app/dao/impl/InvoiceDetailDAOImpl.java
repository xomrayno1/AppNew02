package com.app.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.InvoiceDetailDAO;
import com.app.entity.InvoiceDetail;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDetailDAOImpl extends BaseDAOImpl<InvoiceDetail> implements InvoiceDetailDAO<InvoiceDetail>{
	public List<InvoiceDetail> getInvoiceDetailByInvoice(long invoiceId) {
		// TODO Auto-generated method stub
		Query query = 	factory.getCurrentSession()
				.createQuery(" FROM InvoiceDetail as model where model.activeFlag = 1 and invoice.id = :id order by model.id desc ");
		query.setParameter("id", invoiceId);
		if(query.getResultList() == null || query.getResultList().isEmpty()) {
			return null;
		}else {
			return (List<InvoiceDetail>) query.getResultList();
		}
	}
	 
}
