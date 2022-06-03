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

import com.app.dto.Paging;
import com.app.dto.SupplierDTO;
import com.app.service.SupplierService;
import com.app.utils.Constant;
import com.app.validator.SupplierValidator;

@Controller
@RequestMapping("/manage/supplier")
public class SupplierController {
	@Autowired
	SupplierService supplierService;
	@Autowired
	SupplierValidator supplierValidator;
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if(dataBinder.getTarget().getClass() == SupplierDTO.class) {
			dataBinder.setValidator(supplierValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/manage/supplier/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listSupplier(Model model,@ModelAttribute("searchForm") SupplierDTO supplierDTO,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<SupplierDTO> list = supplierService.getAll(supplierDTO, paging);
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
		return "supplier-list";
	}
	@GetMapping(value = {"/add"})
	public String addSupplier(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new SupplierDTO());
		model.addAttribute("viewOnly", false);
		return "supplier-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewSupplier(Model model,@PathVariable("id") int id) {
		SupplierDTO supplierDTO = supplierService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", supplierDTO);
		model.addAttribute("viewOnly", true);
		return "supplier-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editSupplier(Model model,@PathVariable("id") int id) {
		SupplierDTO supplierDTO = supplierService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", supplierDTO);
		model.addAttribute("viewOnly", false);
		return "supplier-action";
	}
	@PostMapping(value = {"/save"})
	public String saveSupplier(Model model,@ModelAttribute("submitForm") @Validated SupplierDTO supplierDTO
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			if(supplierDTO.getId() != 0) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			model.addAttribute("submitForm", supplierDTO);
			return "supplier-action";
		}
		if(supplierDTO.getId() != 0) {
			try {
				supplierService.update(supplierDTO);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				supplierService.add(supplierDTO);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/manage/supplier/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteSupplier(Model model,@PathVariable("id")int id,HttpSession session) {
		SupplierDTO supplierDTO = supplierService.findById(id);
		try {
			supplierService.delete(supplierDTO);
			session.setAttribute(Constant.MSG_SUCCESS, "Xóa thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xóa thất bại");
		}

		return "redirect:/manage/supplier/list/1";
	}
}
