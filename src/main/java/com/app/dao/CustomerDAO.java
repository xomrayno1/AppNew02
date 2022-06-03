package com.app.dao;

import com.app.entity.Customer;

public interface CustomerDAO<E> extends BaseDAO<E>{
	Customer  findByName(String name);
}
