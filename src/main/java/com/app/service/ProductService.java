package com.app.service;

import java.util.List;

import com.app.dto.Paging;
import com.app.entity.Product;

public interface ProductService {
	void add(Product drug) throws Exception;
	void delete(Product drug) ;
	void update(Product drug) throws Exception;
	List<Product> getAll(Product drug , Paging paging);
	List<Product> getAllByProperty(String property , Object object);
	Product findById(Long id);
}
