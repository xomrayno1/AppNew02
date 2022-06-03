package com.app.service;

import java.util.List;

import com.app.dto.Paging;
import com.app.entity.Invoice;

public interface InvoiceService {
	Long add(Invoice invoice) throws Exception;
	void update(Invoice invoice) throws Exception;
	List<Invoice> getAll(Invoice invoice , Paging paging);
	List<Invoice> getAllByProperty(String property , Object object);
	Invoice findById(long id);
}
