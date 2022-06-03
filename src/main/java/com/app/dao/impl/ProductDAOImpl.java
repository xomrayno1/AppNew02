package com.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.DrugDAO;
import com.app.entity.Product;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductDAOImpl extends BaseDAOImpl<Product> implements DrugDAO<Product>{

}
