package com.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dao.DrugDAO;
import com.app.dto.Paging;
import com.app.entity.Product;
import com.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private DrugDAO<Product> drugDAO;

	public void add(Product drug) throws Exception {
		// TODO Auto-generated method stub
		drug.setActiveFlag(1);
		drug.setCreateDate(new Date());
		drugDAO.insert(drug);
	}

	public void delete(Product drug) {
		// TODO Auto-generated method stub
		drug.setActiveFlag(0);
		drug.setUpdateDate(new Date());
		drugDAO.update(drug);
	}

	public void update(Product drug) throws Exception {
		// TODO Auto-generated method stub
		drug.setUpdateDate(new Date());
		drugDAO.update(drug);
	}

	public List<Product> getAll(Product drug, Paging paging) {
		// TODO Auto-generated method stub
		Map<String, Object> mapParams = new HashedMap();
		StringBuilder queryStr = new StringBuilder();
		if(drug != null) {
			if(!StringUtils.isEmpty(drug.getName())) {
				queryStr.append(" and model.name like :name ");
				mapParams.put("name", "%"+drug.getName()+"%");
			}
		} 
		return drugDAO.findAll(mapParams, queryStr.toString(), paging);
	}

	public List<Product> getAllByProperty(String property, Object object) {
		// TODO Auto-generated method stub
		return drugDAO.findByProperty(property, object);
	}

	public Product findById(Long id) {
		// TODO Auto-generated method stub
		return drugDAO.findById(Product.class, id);
	}
}
