package com.app.service;

import java.util.List;

import com.app.dto.Paging;
import com.app.entity.User;

public interface UserService {
	void add(User user) throws Exception;
	void delete(User user) ;
	void update(User user) throws Exception;
	List<User> getAll(User user , Paging paging);
	List<User> getAllByProperty(String property , Object object);
	User findById(Long id);
	public long getTotalUser();
}
