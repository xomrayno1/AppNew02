package com.app.service;

import java.util.List;

import com.app.dto.Paging;
import com.app.dto.SupplierDTO;
import com.app.entity.Supplier;

public interface SupplierService {
	void add(SupplierDTO supplierDTO) throws Exception;
	void delete(SupplierDTO supplierDTO) ;
	void update(SupplierDTO supplierDTO) throws Exception;
	List<SupplierDTO> getAll(SupplierDTO supplierDTO , Paging paging);
	List<SupplierDTO> getAllByProperty(String property , Object object);
	SupplierDTO findById(long id);
	
	public SupplierDTO findByName(String name);
	
	Supplier findSupplierFindByid(long id);
}
