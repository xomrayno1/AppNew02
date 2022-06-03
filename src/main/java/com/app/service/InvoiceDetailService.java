package com.app.service;

import java.util.List;

import com.app.dto.Paging;
import com.app.entity.InvoiceDetail;

public interface InvoiceDetailService {
	void add(InvoiceDetail invoiceDetail) throws Exception;
	void update(InvoiceDetail invoiceDetail) throws Exception;
	List<InvoiceDetail> getAll(InvoiceDetail invoiceDetail , Paging paging);
	List<InvoiceDetail> getAllByProperty(String property , Object object);
	InvoiceDetail findById(int id);
}
