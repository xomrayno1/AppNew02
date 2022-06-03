package com.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.InvoiceDetailDAO;
import com.app.dto.Paging;
import com.app.entity.InvoiceDetail;
import com.app.service.InvoiceDetailService;
@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService{
	@Autowired
	InvoiceDetailDAO<InvoiceDetail> invoiceDetailDAO;

	public void add(InvoiceDetail invoiceDetail) throws Exception {
		// TODO Auto-generated method stub
		invoiceDetail.setActiveFlag(1);
		invoiceDetail.setCreateDate(new Date());
		  invoiceDetailDAO.insert(invoiceDetail);
	}

	public void update(InvoiceDetail invoiceDetail) throws Exception {
		// TODO Auto-generated method stub
		invoiceDetail.setUpdateDate(new Date());
		invoiceDetailDAO.update(invoiceDetail);
	}

	public List<InvoiceDetail> getAll(InvoiceDetail invoiceDetail, Paging paging) {
		// TODO Auto-generated method stub
		Map<String, Object> mapParams = new HashedMap<String, Object>();
		StringBuilder queryStr = new StringBuilder();
		 
		queryStr.append(" ORDER BY (model.id) DESC");
		 
		return invoiceDetailDAO.findAll(mapParams, queryStr.toString(), paging);
	}

	public List<InvoiceDetail> getAllByProperty(String property, Object object) {
		// TODO Auto-generated method stub
		 
		return invoiceDetailDAO.findByProperty(property, object);
	}

	public InvoiceDetail findById(int id) {
		// TODO Auto-generated method stub
		return invoiceDetailDAO.findById(InvoiceDetail.class, id);
	}
}
