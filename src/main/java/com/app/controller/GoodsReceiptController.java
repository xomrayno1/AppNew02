package com.app.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dao.InvoiceDAO;
import com.app.dao.InvoiceDetailDAO;
import com.app.dto.Paging;
import com.app.dto.ReportValue;
import com.app.dto.SearchReportForm;
import com.app.dto.SupplierDTO;
import com.app.entity.Product;
import com.app.entity.User;
import com.app.entity.Invoice;
import com.app.entity.InvoiceDetail;
import com.app.service.ProductService;
import com.app.service.InvoiceDetailService;
import com.app.service.InvoiceService;
import com.app.service.SupplierService;
import com.app.utils.Constant;
import com.app.validator.InvoiceValidator;
 

@Controller
@RequestMapping("/goods-receipt")
public class GoodsReceiptController {

	@Autowired
	InvoiceService invoiceService;
	@Autowired
	InvoiceDetailService invoiceDetailService;
	
	@Autowired
	InvoiceDAO<Invoice> invoiceDAO;
	
	@Autowired
	InvoiceDetailDAO<InvoiceDetail> invoiceDetailDAO;
	
	@Autowired
	InvoiceValidator invoiceValidator;
	@Autowired
	ProductService productService;
 
	@Autowired
	SupplierService supplierService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if(dataBinder.getTarget().getClass() == Invoice.class) {
			dataBinder.setValidator(invoiceValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/goods-receipt/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listInvoice(Model model,@ModelAttribute("searchForm") Invoice invoice,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		invoice.setInvoiceType(Constant.GOODS_RECEIPT);
		List<Invoice> list = invoiceService.getAll(invoice, paging);
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
		 
		return "goods-receipt-list";
	}
	@GetMapping(value = {"/add/{page}"})
	public String addInvoice(Model model, @PathVariable("page") int page ,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Product> list = productService.getAll(null, paging);
		model.addAttribute("pageInfo", paging);
		model.addAttribute("listProduct", list);
		model.addAttribute("title", "Nhập hàng");
		model.addAttribute("submitForm", new Invoice(Constant.GOODS_RECEIPT));
		model.addAttribute("viewOnly", false);
		innitSelect(model);
		
		if(session.getAttribute(Constant.MSG_ERROR) != null) {
			System.out.println(session.getAttribute(Constant.MSG_ERROR));
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		if(session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		return "goods-receipt-action";
	}
	
	@GetMapping(value = {"/view/{id}"})
	public String viewInvoice(Model model,@PathVariable("id") int id) {
		Invoice invoice = invoiceService.findById(id);
		List<InvoiceDetail> listInvoiceDetails = 	invoiceDetailDAO.getInvoiceDetailByInvoice(id);
		invoice.setListInvoice(listInvoiceDetails);
		model.addAttribute("title", "Xem chi tiết ");
		model.addAttribute("submitForm", invoice);
		model.addAttribute("invoice", invoice);
		model.addAttribute("viewOnly", true);
		innitSelect(model);
		return "goods-receipt-action";
	}
	
	public void innitSelect(Model model) {
		List<Product> list = productService.getAll(null, null);
		Collections.sort(list,new Comparator<Product>() {
			public int compare(Product o1, Product o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("listProduct", list);
		List<SupplierDTO> listSupp = supplierService.getAll(null, null);
		Collections.sort(listSupp,new Comparator<SupplierDTO>() {
			public int compare(SupplierDTO o1, SupplierDTO o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("listSupp", listSupp);
	}

	
	@GetMapping(value = {"/order"})
	public String orderProduct(@RequestParam("id") long id, HttpServletRequest request ) {
		HttpSession session = request.getSession();
		Invoice invoice = (Invoice) session.getAttribute("invoice");
		Product product = productService.findById(id);
		
		try {
			InvoiceDetail invoiceDetail = new InvoiceDetail();
			invoiceDetail.setQuantity(1);
			invoiceDetail.setTotalPrice(new BigDecimal(product.getPrice() * 1));
			invoiceDetail.setProduct(product);
			invoiceDetail.setPrice(new BigDecimal(product.getPrice()));
			invoiceDetail.setInvoice(invoice);
			invoiceDetail.setActiveFlag(1);
			if(invoice == null) {
				invoice = new Invoice();
				invoice.setObjectId(null);
				invoice.setObjectType(Constant.OBJECT_TYPE_SUPPLIER);
				invoice.addItem(invoiceDetail);
			}else {
				boolean flag = true;
				for(InvoiceDetail item : invoice.getListInvoice()) {
					if(item.getProduct().getId() == id) {
						item.setQuantity(item.getQuantity() + 1);
						item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
						 
						flag = false;
					}
				}
				if(flag) {
					invoice.addItem(invoiceDetail);
				}
				invoice.setObjectId(null);
				invoice.setObjectType(Constant.OBJECT_TYPE_SUPPLIER);
				invoice.calTotalPrice();
			}
			session.setAttribute(Constant.MSG_SUCCESS, "Đặt thành công");
		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute(Constant.MSG_ERROR, "Đặt thất bại");
		}
		
		session.setAttribute("invoice", invoice);
		return "redirect:/goods-receipt/add/1";
	}
	@GetMapping(value = {"/remove/{id}"})
	public String removeItem(@PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Invoice orders = (Invoice) session.getAttribute("invoice");
		try {
			if(orders != null) {
				orders.removeItem(id);
				session.setAttribute(Constant.MSG_SUCCESS, "Xoá thành công");
				session.setAttribute("invoice", orders);
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
		}
 
		session.setAttribute("invoice", orders);
		return "redirect:/goods-receipt/add/1";
	}
	@GetMapping(value = {"/cancel"})
	public String cancelInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			Invoice orders = (Invoice) session.getAttribute("invoice");
			if(orders != null) {
				session.removeAttribute("invoice");
				session.setAttribute(Constant.MSG_SUCCESS, "Huỷ thành công");
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Huỷ thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Huỷ thất bại");
		}
		return "redirect:/goods-receipt/add/1";
	}
	
	@RequestMapping(value = {"/pay"})
	public String payInvoice(HttpServletRequest request, @ModelAttribute("submitForm") Invoice submitForm) {
		HttpSession session = request.getSession();
		Invoice orders = (Invoice) session.getAttribute("invoice");
		User user = (User) session.getAttribute(Constant.USER_INFO);
		try {
			if(orders != null) {
				orders.setActiveFlag(1);
				orders.setInvoiceType(Constant.GOODS_RECEIPT);
				orders.setObjectId(submitForm.getObjectId());
				orders.setObjectType(Constant.OBJECT_TYPE_SUPPLIER);
				orders.setUser(user);
				long id = invoiceService.add(orders);
				for(InvoiceDetail item : orders.getListInvoice()) {
					Invoice invoice = invoiceService.findById(id);
					item.setInvoice(invoice);
					invoiceDetailService.add(item);
					Product product = productService.findById(item.getProduct().getId());
					product.setQuantity(product.getQuantity() + item.getQuantity());
					productService.update(product);
				}
				session.removeAttribute("invoice");
				session.setAttribute(Constant.MSG_SUCCESS, "Thanh toán thành công");
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Thanh toán thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Thanh toán thất bại");
		}
		return "redirect:/goods-receipt/add/1";
	}
	
	@RequestMapping("/report/list")
	public String reportList(Model model,  @ModelAttribute("searchForm") SearchReportForm searchReportForm 
			,HttpSession session) {
		 
		List<ReportValue> reportValues = new ArrayList<ReportValue>();
		searchReportForm.setInvoiceType(Constant.GOODS_RECEIPT);
		List<Map<String, Object>> maps = invoiceDAO.reportReceipt(searchReportForm);
		// p.id, p.name, sum(id.quantity) as quantity, month(i.createDate) as month, year(i.createDate) as year
		for(Map<String, Object> map : maps) {
			ReportValue reportValue = new ReportValue();
			reportValue.setId(map.get("id"));
			reportValue.setProductName(map.get("name"));
			reportValue.setQuantity(map.get("quantity"));
			reportValue.setMonth(map.get("month"));
			reportValue.setYear(map.get("year"));
			reportValues.add(reportValue);
		}
		
		List<String> lstMonth = new ArrayList<String>();
		for(int i = 1 ; i<=12 ; i++) {
			lstMonth.add("Tháng "+i);
		}
		model.addAttribute("lstMonth", lstMonth);
		model.addAttribute("list", reportValues);
		
		List<Product> lstProduct = productService.getAll(null, null);
		Collections.sort(lstProduct,new Comparator<Product>() {
			public int compare(Product o1, Product o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("lstProduct", lstProduct);
		
		
		return "goods-receipt-report";
	}
	

}
