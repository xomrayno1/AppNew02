package com.app.service;

import java.util.List;

import com.app.dto.CustomerDTO;
import com.app.dto.Paging;

public interface CustomerService {
	void add(CustomerDTO customerDTO) throws Exception;
	void delete(CustomerDTO customerDTO) ;
	void update(CustomerDTO customerDTO) throws Exception;
	List<CustomerDTO> getAll(CustomerDTO customerDTO , Paging paging);
	List<CustomerDTO> getAllByProperty(String property , Object object);
	CustomerDTO findById(long id);
	
	CustomerDTO  findByName(String name);
}
