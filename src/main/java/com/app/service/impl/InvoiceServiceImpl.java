package com.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.InvoiceDAO;
import com.app.dto.Paging;
import com.app.entity.Invoice;
import com.app.service.InvoiceService;
@Service
public class InvoiceServiceImpl implements InvoiceService{
	@Autowired
	InvoiceDAO<Invoice> invoiceDAO;

	public Long add(Invoice invoice) throws Exception {
		// TODO Auto-generated method stub
		invoice.setActiveFlag(1);
		invoice.setCreateDate(new Date());
		return invoiceDAO.save(invoice);
	}

	public void update(Invoice invoice) throws Exception {
		// TODO Auto-generated method stub
		 
		invoice.setUpdateDate(new Date());
		 invoiceDAO.update(invoice);
	}

	public List<Invoice> getAll(Invoice invoice, Paging paging) {
		// TODO Auto-generated method stub
		Map<String, Object> mapParams = new HashedMap<String, Object>();
		StringBuilder queryStr = new StringBuilder();
		if(invoice != null) {
			if(invoice.getInvoiceType() != 0) {
				queryStr.append(" and model.invoiceType = :invoiceType ");
				mapParams.put("invoiceType", invoice.getInvoiceType());
			}
			if(invoice.getFromDate() != null && invoice.getToDate() != null) {
				queryStr.append(" and date(model.createDate ) between :fromDate and :toDate ");
				mapParams.put("fromDate", invoice.getFromDate());
				mapParams.put("toDate", invoice.getToDate());
			}
		}
		queryStr.append(" ORDER BY (model.id) DESC");
		 
		return invoiceDAO.findAll(mapParams, queryStr.toString(), paging);
	}

	public List<Invoice> getAllByProperty(String property, Object object) {
		// TODO Auto-generated method stub
		 
		return invoiceDAO.findByProperty(property, object);
	}

	public Invoice findById(long id) {
		// TODO Auto-generated method stub
		return invoiceDAO.findById(Invoice.class, id);
	}
}
