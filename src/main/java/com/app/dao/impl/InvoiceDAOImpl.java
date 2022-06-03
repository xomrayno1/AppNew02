package com.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.InvoiceDAO;
import com.app.dto.SearchReportForm;
import com.app.entity.Invoice;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO<Invoice>{

	public Long save(Invoice invoice) {
		// TODO Auto-generated method stub
		return (Long) factory.getCurrentSession().save(invoice);
	}
 
	
	public List<Map<String, Object>> getBarChartByMonth(int year, Integer objectType) {
		// TODO Auto-generated method stub
		String Sql = "select MONTH(i.createDate) month,  sum(i.total_price) totalPrice from invoice i where i.object_type = ? and YEAR(i.createDate) = ? GROUP by month(i.createDate)";
			 
		org.hibernate.query.Query  query = factory.getCurrentSession().createNativeQuery(Sql);
		query.setParameter(1, objectType);
		query.setParameter(2, year);
		List<Map<String , Object>> barCharts  = 
				query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		
		return barCharts ;
	}


	@Override
	public List<Map<String, Object>> report(SearchReportForm searchReportForm) {
		// TODO Auto-generated method stub
		 
		 
		StringBuilder builder = new StringBuilder();
		builder.append(" select sum(i.total_price) price, u.username as name, i.user_id as userId  from Invoice i  ");
		builder.append(" inner join user u on u.id = i.user_id ");
		builder.append(" where i.invoice_type = ?  ");
		if(searchReportForm.getFromDate() != null && searchReportForm.getToDate() != null) {
			builder.append("and i.createDate >= ? and i.createDate <= ?");
		}
		if(searchReportForm.getUserId() != null) {
			builder.append("and i.user_id = ?");
		}
		//and (day(i.createDate) = ? and month(i.createDate) = ? and year(i.createDate) = ?)
		builder.append(" group by i.user_id  ");
		
		org.hibernate.query.Query  query = factory.getCurrentSession().createNativeQuery(builder.toString());
		int index = 1;
		query.setParameter(index++, searchReportForm.getInvoiceType());
		if(searchReportForm.getFromDate() != null && searchReportForm.getToDate() != null) {
			query.setParameter(index++, searchReportForm.getFromDate());
			query.setParameter(index++, searchReportForm.getToDate());
		}
		if(searchReportForm.getUserId() != null) {
			query.setParameter(index++, searchReportForm.getUserId());
		}
 
		List<Map<String , Object>> barCharts  = 
				query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		
		return barCharts ;
	}


	@Override
	public List<Map<String, Object>> reportReceipt(SearchReportForm searchReportForm) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(" select p.id, p.name, sum(id.quantity) as quantity, month(i.createDate) as month, year(i.createDate) as year from Invoice i   ");
		builder.append(" INNER JOIN InvoiceDetail id on id.invoice_id = i.id ");
		builder.append(" INNER join Product p on p.id = id.product_id ");
		builder.append(" where i.invoice_type = ?  ");
		if(searchReportForm.getMonth() != null) {
			builder.append("and month(i.createDate) = ? ");
		}
		if(searchReportForm.getProductId() != null) {
			builder.append("and  id.product_id = ?");
		}
		
		
		//and (day(i.createDate) = ? and month(i.createDate) = ? and year(i.createDate) = ?)
		builder.append(" group by p.id,  p.name, month(i.createDate), year(i.createDate) ");
		int index = 1;
		org.hibernate.query.Query  query = factory.getCurrentSession().createNativeQuery(builder.toString());
		query.setParameter(index++, searchReportForm.getInvoiceType());
		if(searchReportForm.getMonth() != null) {
			query.setParameter(index++, searchReportForm.getMonth());
		}
		if(searchReportForm.getProductId() != null) {
			query.setParameter(index++, searchReportForm.getProductId());
		}
 
		List<Map<String , Object>> barCharts  = 
				query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		
		return barCharts ;
	}
 
}
