package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.dto.SupplierDTO;
import com.app.service.SupplierService;

@Component
public class SupplierValidator   implements Validator{
	@Autowired
	SupplierService supplierService;

	 
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(SupplierDTO.class);
	}

 
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		SupplierDTO supplierDTO = (SupplierDTO) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "error.required");
		 
	 
		
		if(!StringUtils.isEmpty(supplierDTO.getName())) {
			List<SupplierDTO> categories = supplierService.getAllByProperty("code", supplierDTO.getCode());		
			if(supplierDTO.getId() != 0) {
				if(!categories.isEmpty()) {
					SupplierDTO current = supplierService.findById(supplierDTO.getId());
					if(!supplierDTO.getCode().equals(current.getCode())) {
						errors.rejectValue("code", "error.exists");
					}
				}
			}else {
				if(!categories.isEmpty()) {
					errors.rejectValue("code", "error.exists");
				}
			}
		}
	}

}
