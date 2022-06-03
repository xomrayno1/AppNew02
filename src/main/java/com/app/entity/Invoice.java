package com.app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="object_type")
	private Integer objectType;
	@Column(name="invoice_type")
	private Integer invoiceType;
	@Column(name="object_id")
	private Long objectId;
	@Column(name="total_price")
	private BigDecimal totalPrice;
	@Temporal(TemporalType.DATE)
	private Date createDate;
	@Temporal(TemporalType.DATE)
	private Date updateDate;
	private int activeFlag;

	@Transient
	private Date fromDate;
	@Transient
	private Date toDate;
	@Column(name="object_name")
	private String objectName;
	

	@OneToMany(mappedBy = "invoice")
	private List<InvoiceDetail> listInvoice = new ArrayList<InvoiceDetail>();
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	 
	
	public Invoice(Integer invoiceType) {
		 
		this.invoiceType = invoiceType;
	}
	public Invoice() {
		 
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public List<InvoiceDetail> getListInvoice() {
		return listInvoice;
	}
	public void setListInvoice(List<InvoiceDetail> listInvoice) {
		this.listInvoice = listInvoice;
	}
	
	public void addItem(InvoiceDetail detail){
		listInvoice.add(detail);
		calTotalPrice();
	}
	public void calTotalPrice() {
		BigDecimal total = new BigDecimal(0);
		for(InvoiceDetail item : listInvoice) {
			total = total.add(item.getTotalPrice());
		}
		this.totalPrice = total;
	}
	
	public void removeItem(long id) {
		Iterator<InvoiceDetail> iterator = listInvoice.iterator();
		while(iterator.hasNext()) {
			InvoiceDetail od = 	iterator.next();
			if(od.getProduct().getId() == id) {
				iterator.remove();
			}
		}
		calTotalPrice();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
 
	 
}
