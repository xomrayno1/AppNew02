package com.app.dto;

import java.math.BigDecimal;

public class ReportValue {
	private String name;
	private BigDecimal price;
	private Object userId;
	
	//receipt
	 
	 
	 private Object id;
	 private Object productName;
	 private Object quantity;
	 private Object month;
	 private Object year;
	
	
	
 
	public ReportValue() {
		super();
	}




	public ReportValue(String name, BigDecimal price, Object userId) {
	 
		this.name = name;
		this.price = price;
		this.userId  = userId;
		 
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public BigDecimal getPrice() {
		return price;
	}




	public void setPrice(BigDecimal price) {
		this.price = price;
	}




	public Object getUserId() {
		return userId;
	}




	public void setUserId(Object userId) {
		this.userId = userId;
	}




	public Object getId() {
		return id;
	}




	public void setId(Object id) {
		this.id = id;
	}




	public Object getProductName() {
		return productName;
	}




	public void setProductName(Object productName) {
		this.productName = productName;
	}




	public Object getQuantity() {
		return quantity;
	}




	public void setQuantity(Object quantity) {
		this.quantity = quantity;
	}




	public Object getMonth() {
		return month;
	}




	public void setMonth(Object month) {
		this.month = month;
	}




	public Object getYear() {
		return year;
	}




	public void setYear(Object year) {
		this.year = year;
	}
 
 
	

}
