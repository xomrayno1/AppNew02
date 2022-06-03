package com.app.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
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
import com.app.entity.Product;
import com.app.entity.Supplier;
import com.app.service.ProductService;
import com.app.service.SupplierService;
import com.app.utils.Constant;
import com.app.utils.ConvertDTO;
import com.app.validator.ProductValidator;

@Controller
@RequestMapping("/manage/product")
public class ProductsController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductValidator productValidator;
	@Autowired
	SupplierService supplierService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if(dataBinder.getTarget().getClass() == Product.class) {
			dataBinder.setValidator(productValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/manage/product/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listProduct(Model model,@ModelAttribute("searchForm") Product product,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Product> list = productService.getAll(product, paging);
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
		return "product-list";
	}
	@GetMapping(value = {"/add"})
	public String addProduct(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new Product());
		model.addAttribute("viewOnly", false);
		innitSelect(model);
		return "product-action";
	}

	@GetMapping(value = {"/view/{id}"})
	public String viewProduct(Model model,@PathVariable("id") Long id) {
		Product product = productService.findById(id);
		product.setSupplierId(product.getSupplier().getId());
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", product);
		model.addAttribute("viewOnly", true);
		innitSelect(model);
		return "product-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editProduct(Model model,@PathVariable("id") Long id) {
		Product product = productService.findById(id);
		product.setSupplierId(product.getSupplier().getId());
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", product);
		model.addAttribute("viewOnly", false);
		innitSelect(model);
		return "product-action";
	}
	@PostMapping(value = {"/save"})
	public String saveProduct(Model model,@ModelAttribute("submitForm") @Validated Product product 
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			if(product.getId() != null) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			innitSelect(model);
			model.addAttribute("submitForm", product);
			return "product-action";
		}
		if(product.getId() != null) {
			try {
				Supplier supplier = supplierService.findSupplierFindByid(product.getSupplierId());
				product.setSupplier(supplier);
				productService.update(product);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				Supplier supplier = supplierService.findSupplierFindByid(product.getSupplierId());
				 
				product.setSupplier(supplier);
				productService.add(product);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/manage/product/list/1";
	}
	
	public void innitSelect(Model model) {
		List<SupplierDTO> listSupp = supplierService.getAll(null, null);		
		Collections.sort(listSupp,new Comparator<SupplierDTO>() {
			public int compare(SupplierDTO o1, SupplierDTO o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("listSupp", listSupp);
	}
	
	@GetMapping(value = {"/delete/{id}"})
	public String deleteProduct(Model model,@PathVariable("id") Long id,HttpSession session) {
		Product product = productService.findById(id);
		try {
			productService.delete(product);
			session.setAttribute(Constant.MSG_SUCCESS, "Xóa thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xóa thất bại");
		}

		return "redirect:/manage/product/list/1";
	}
}
