package com.app.utils;

import com.app.dto.CustomerDTO;
import com.app.dto.SupplierDTO;
import com.app.entity.Customer;
import com.app.entity.Supplier;

public class ConvertDTO {
 
	public static SupplierDTO convertSupplierToDTO(Supplier supplier) {
		SupplierDTO supplierDTO = new SupplierDTO();
		supplierDTO.setActiveFlag(supplier.getActiveFlag());
		supplierDTO.setAddress(supplier.getAddress());
		supplierDTO.setCode(supplier.getCode());
		supplierDTO.setCreateDate(supplier.getCreateDate());
		supplierDTO.setEmail(supplier.getEmail());
		supplierDTO.setId(supplier.getId());
		supplierDTO.setName(supplier.getName());
		supplierDTO.setPhone(supplier.getPhone());
		supplierDTO.setUpdateDate(supplier.getUpdateDate());
		return supplierDTO;
	}
	
	public static CustomerDTO convertCustomerToDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setActiveFlag(customer.getActiveFlag());
		customerDTO.setAddress(customer.getAddress());
		customerDTO.setCreateDate(customer.getCreateDate());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		customerDTO.setPhone(customer.getPhone());
		customerDTO.setUpdateDate(customer.getUpdateDate());
		return customerDTO;
	}
	 
}
