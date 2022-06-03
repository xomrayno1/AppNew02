package com.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dao.UserDAO;
import com.app.dto.Paging;
import com.app.entity.User;
import com.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO<User> userDAO;

	public void add(User user) throws Exception {
		// TODO Auto-generated method stub
		user.setActiveFlag(1);
		user.setCreateDate(new Date());
		userDAO.insert(user);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		user.setActiveFlag(0);
		user.setUpdateDate(new Date());
		userDAO.update(user);
	}

	public void update(User user) throws Exception {
		// TODO Auto-generated method stub
		user.setUpdateDate(new Date());
		userDAO.update(user);
	}

	public List<User> getAll(User user, Paging paging) {
		// TODO Auto-generated method stub
		Map<String, Object> mapParams = new HashedMap();
		StringBuilder queryStr = new StringBuilder();
		if(user != null) {
			
			if(!StringUtils.isEmpty(user.getName())) {
				queryStr.append(" and model.name like :name ");
				mapParams.put("name", "%"+user.getName()+"%");
			}
			if(!StringUtils.isEmpty(user.getUsername())) {
				queryStr.append(" and model.username like :username ");
				mapParams.put("username", "%"+user.getUsername()+"%");
			}
			if(user.getRole() != null ) {
				queryStr.append(" and model.role = :roleId ");
				mapParams.put("roleId", user.getRole());
			}
			
		}
 
		return userDAO.findAll(mapParams, queryStr.toString(), paging);
	}

	public List<User> getAllByProperty(String property, Object object) {
		// TODO Auto-generated method stub
		return userDAO.findByProperty(property, object);
	}

	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userDAO.findById(User.class, id);
	}
 

	public long getTotalUser() {
		// TODO Auto-generated method stub
		return 0;
	}
 
}
