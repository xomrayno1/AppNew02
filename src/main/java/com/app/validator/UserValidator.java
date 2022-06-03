package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entity.User;
import com.app.service.UserService;

@Component
public class UserValidator implements Validator{
	@Autowired
	UserService userService;

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(User.class);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");
 
		if(!StringUtils.isEmpty(user.getUsername())) {
			List<User> users = userService.getAllByProperty("username", user.getUsername());		
			if(user.getId()!= null ) {
				if(!users.isEmpty()) {
					User current = userService.findById(user.getId());
					if(!user.getUsername().equals(current.getUsername())) {
						errors.rejectValue("username", "error.exists");
					}
				}
			}else {
				if(!users.isEmpty()) {
					errors.rejectValue("username", "error.exists");
				}
			}
		}
		
		if(!StringUtils.isEmpty(user.getEmail())) {
			List<User> users = userService.getAllByProperty("email", user.getEmail());		
			if(user.getId()!= null ) {
				if(!users.isEmpty()) {
					User current = userService.findById(user.getId());
					if(!user.getEmail().equals(current.getEmail())) {
						errors.rejectValue("email", "error.exists");
					}
				}
			}else {
				if(!users.isEmpty()) {
					errors.rejectValue("email", "error.exists");
				}
			}
		}
	}
}
