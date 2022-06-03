package com.app.dao;

import com.app.entity.Supplier;

public interface SupplierDAO<E> extends BaseDAO<E> {
	Supplier  findByName(String name);
}
