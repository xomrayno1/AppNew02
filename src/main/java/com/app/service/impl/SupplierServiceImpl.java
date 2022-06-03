package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dao.SupplierDAO;
import com.app.dto.Paging;
import com.app.dto.SupplierDTO;
import com.app.entity.Supplier;
import com.app.service.SupplierService;
import com.app.utils.ConvertDTO;

@Service
public class SupplierServiceImpl  implements SupplierService{
	@Autowired
	SupplierDAO<Supplier> supplierDAO;
	
	public void add(SupplierDTO supplierDTO) throws Exception {
		// TODO Auto-generated method stub
		Supplier supplier = new Supplier();
		supplier.setActiveFlag(1);
		supplier.setCreateDate(new Date());
		supplier.setUpdateDate(new Date());
		supplier.setName(supplierDTO.getName());
		supplier.setCode(supplierDTO.getCode());
		supplier.setAddress(supplierDTO.getAddress());
		supplier.setEmail(supplierDTO.getEmail());
		supplier.setPhone(supplierDTO.getPhone()); 
		supplierDAO.insert(supplier);
	}

	 
	public void delete(SupplierDTO supplierDTO) {
		// TODO Auto-generated method stub
		Supplier supplier = new Supplier();
		supplier.setActiveFlag(0);
		supplier.setId(supplierDTO.getId());
		supplier.setCreateDate(supplierDTO.getCreateDate());
		supplier.setUpdateDate(new Date());
		supplier.setName(supplierDTO.getName());
		supplier.setCode(supplierDTO.getCode());
		supplier.setAddress(supplierDTO.getAddress());
		supplier.setEmail(supplierDTO.getEmail());
		supplier.setPhone(supplierDTO.getPhone()); 
		supplierDAO.delete(supplier);
	}

	 
	public void update(SupplierDTO supplierDTO) throws Exception {
		// TODO Auto-generated method stub
		Supplier supplier = new Supplier();
		supplier.setActiveFlag(supplierDTO.getActiveFlag());
		supplier.setCreateDate(supplierDTO.getCreateDate());
		supplier.setUpdateDate(new Date());
		supplier.setName(supplierDTO.getName());
		supplier.setCode(supplierDTO.getCode());
		supplier.setAddress(supplierDTO.getAddress());
		supplier.setEmail(supplierDTO.getEmail());
		supplier.setPhone(supplierDTO.getPhone());
		supplier.setId(supplierDTO.getId());
		supplierDAO.update(supplier);
	}

	 
	public List<SupplierDTO> getAll(SupplierDTO supplierDTO, Paging paging) {
		// TODO Auto-generated method stub
		Map<String, Object> mapParams = new HashedMap<String, Object>();
		StringBuilder queryStr = new StringBuilder();
		if(supplierDTO != null) {
 
			if(!StringUtils.isEmpty(supplierDTO.getName())) {
				queryStr.append(" and model.name like :name ");
				mapParams.put("name", "%"+supplierDTO.getName()+"%");
			}
		} 
		List<SupplierDTO> list = new ArrayList<SupplierDTO>();
		for(Supplier supplier : supplierDAO.findAll(mapParams, queryStr.toString(), paging)) {
			SupplierDTO dto = ConvertDTO.convertSupplierToDTO(supplier);
			list.add(dto);
		}
		return list;
	}

	 
	public List<SupplierDTO> getAllByProperty(String property, Object object) {
		// TODO Auto-generated method stub
		List<SupplierDTO> list = new ArrayList<SupplierDTO>();
		for(Supplier supplier : supplierDAO.findByProperty(property, object)) {
			SupplierDTO dto = ConvertDTO.convertSupplierToDTO(supplier);
			list.add(dto);
		}
		return list;
	}
 
	public SupplierDTO findById(long id) {
		// TODO Auto-generated method stub
		Supplier supplier = supplierDAO.findById(Supplier.class, id);
		SupplierDTO dto = ConvertDTO.convertSupplierToDTO(supplier);
		return dto;
	}

	 
	public SupplierDTO findByName(String name) {
		// TODO Auto-generated method stub
		Supplier supplier = supplierDAO.findByName(name);
		SupplierDTO dto = ConvertDTO.convertSupplierToDTO(supplier);
		return dto;
	}


	@Override
	public Supplier findSupplierFindByid(long id) {
		// TODO Auto-generated method stub
		return supplierDAO.findById(Supplier.class, id);
	}

}
