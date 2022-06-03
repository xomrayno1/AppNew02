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
public class LoginValidator  implements Validator{
	@Autowired
	UserService userService;
	
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(User.class);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.required");
		User user = (User) target;
		if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
			List<User> currentUsers = userService.getAllByProperty("username", user.getUsername());
			if(currentUsers != null  && currentUsers.size() > 0) {
				User currentUser = currentUsers.get(0);	
				if(!user.getPassword().equals(currentUser.getPassword())) {
					errors.rejectValue("password", "error.login");
				}
			}else {
				errors.rejectValue("password", "error.login");
			}
		}
	}
}
