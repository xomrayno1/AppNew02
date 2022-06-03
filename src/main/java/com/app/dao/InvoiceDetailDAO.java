package com.app.dao;

import java.util.List;

import com.app.entity.InvoiceDetail;

public interface InvoiceDetailDAO<E> extends BaseDAO<E> {
	public List<InvoiceDetail> getInvoiceDetailByInvoice(long invoiceId);
}
