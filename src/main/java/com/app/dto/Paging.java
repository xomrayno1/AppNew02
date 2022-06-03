package com.app.dto;

public class Paging {
	private int numberPerPage;
	private int indexPage;
	private int offSet;
	private long totalPage;
	private long totalProduct;
	
	public Paging(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	
	}
	public Paging() {
		
	}
	public int getNumberPerPage() {
		return numberPerPage;
	}
	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}
	public int getIndexPage() {
		return indexPage;
	}
	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}
	public int getOffSet() {
		this.offSet =    (numberPerPage * indexPage) - numberPerPage ;
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	public long getTotalPage() {
		this.totalPage = (long) Math.ceil(totalProduct / (double) numberPerPage);
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public long getTotalProduct() {
		return totalProduct;
	}
	public void setTotalProduct(long totalProduct) {
		this.totalProduct = totalProduct;
	}
}
