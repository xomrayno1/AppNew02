package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.dto.CustomerDTO;
import com.app.service.CustomerService;
@Component
public class CustomerValidator implements Validator {

	@Autowired
	CustomerService customerService;
 
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(CustomerDTO.class);
	}

	 
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CustomerDTO customerDTO = (CustomerDTO) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");
		 

		if(!StringUtils.isEmpty(customerDTO.getName())) {
			List<CustomerDTO> categories = customerService.getAllByProperty("name", customerDTO.getName());		
			if(customerDTO.getId() != 0) {
				if(!categories.isEmpty()) {
					CustomerDTO current = customerService.findById(customerDTO.getId());
					if(!customerDTO.getName().equals(current.getName())) {
						errors.rejectValue("name", "error.exists");
					}
				}
			}else {
				if(!categories.isEmpty()) {
					errors.rejectValue("name", "error.exists");
				}
			}
		}
	}
	
}
