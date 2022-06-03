package com.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.entity.Invoice;
import com.app.service.InvoiceService;

@Component
public class InvoiceValidator implements Validator {
	@Autowired
	InvoiceService invoiceService;

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Invoice.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "error.required");
//		InvoiceDTO invoiceDTO = (InvoiceDTO)  target;
//		if(invoiceDTO.getProductId() == 0) {
//			errors.rejectValue("productId", "error.required");
//		}
//		if(invoiceDTO.getQuantity() == 0) {
//			errors.rejectValue("quantity", "error.required");
//		}
//		if(invoiceDTO.getInvenId() == 0) {
//			errors.rejectValue("invenId", "error.required");
//		}
//		if(invoiceDTO.getType() == Constant.GOODS_RECEIPT) {
//			if(invoiceDTO.getSuppId() == 0 ) {
//				errors.rejectValue("suppId", "error.required");
//			}
//		}else if(invoiceDTO.getType() == Constant.GOODS_ISSUE) {
//			if(invoiceDTO.getCusId() == 0 ) {
//				errors.rejectValue("cusId", "error.required");
//			}
//		}
	}

}
