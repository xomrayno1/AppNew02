package com.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dto.CustomerDTO;
import com.app.dto.Paging;
import com.app.service.CustomerService;
import com.app.utils.Constant;
import com.app.validator.CustomerValidator;
@Controller
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	CustomerService  customerService;
	@Autowired
	CustomerValidator customerValidator;
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if(dataBinder.getTarget().getClass() == CustomerDTO.class) {
			dataBinder.setValidator(customerValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/customers/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listCategory(Model model,@ModelAttribute("searchForm") CustomerDTO customerDTO,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<CustomerDTO> list = customerService.getAll(customerDTO, paging);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", paging);
		if(session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		if(session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		return "customer-list";
	}
	@GetMapping(value = {"/add"})
	public String addCustomer(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new CustomerDTO());
		model.addAttribute("viewOnly", false);
		return "customer-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewCustomer(Model model,@PathVariable("id") int id) {
		CustomerDTO customerDTO = customerService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", customerDTO);
		model.addAttribute("viewOnly", true);
		return "customer-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editCustomer(Model model,@PathVariable("id") int id) {
		CustomerDTO customerDTO = customerService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", customerDTO);
		model.addAttribute("viewOnly", false);
		return "customer-action";
	}
	@PostMapping(value = {"/save"})
	public String saveCustomer(Model model,@ModelAttribute("submitForm") @Validated CustomerDTO customerDTO
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			if(customerDTO.getId() != 0) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			model.addAttribute("submitForm", customerDTO);
			return "customer-action";
		}
		if(customerDTO.getId() != 0) {
			try {
				customerService.update(customerDTO);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				customerService.add(customerDTO);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/customers/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteCustomer(Model model,@PathVariable("id")int id,HttpSession session) {
		CustomerDTO customerDTO = customerService.findById(id);
		try {
			customerService.delete(customerDTO);
			session.setAttribute(Constant.MSG_SUCCESS, "Xóa thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xóa thất bại");
		}

		return "redirect:/customers/list/1";
	}
}
