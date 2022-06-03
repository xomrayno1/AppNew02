package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.dto.SearchReportForm;
import com.app.entity.Invoice;

public interface InvoiceDAO<E> extends BaseDAO<E> {
	Long save(Invoice invoice);
	
	public List<Map<String, Object>> getBarChartByMonth(int year, Integer objectType );
	
	public List<Map<String, Object>> report(SearchReportForm searchReportForm);
	
	public List<Map<String, Object>> reportReceipt(SearchReportForm searchReportForm);
}
