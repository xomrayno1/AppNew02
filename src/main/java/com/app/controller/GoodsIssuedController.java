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
import com.app.dto.CustomerDTO;
import com.app.dto.Paging;
import com.app.dto.ReportValue;
import com.app.dto.SearchReportForm;
import com.app.entity.Invoice;
import com.app.entity.InvoiceDetail;
import com.app.entity.Product;
import com.app.entity.User;
import com.app.service.CustomerService;
import com.app.service.InvoiceDetailService;
import com.app.service.InvoiceService;
import com.app.service.ProductService;
import com.app.service.UserService;
import com.app.utils.Constant;
import com.app.validator.InvoiceValidator;

@Controller
@RequestMapping("/goods-issue")
public class GoodsIssuedController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	InvoiceDetailDAO<InvoiceDetail> invoiceDetailDAO;
	
	@Autowired
	InvoiceDAO<Invoice> invoiceDAO;
	
	@Autowired
	InvoiceDetailService invoiceDetailService; 
	
	@Autowired
	InvoiceValidator invoiceValidator;
	@Autowired
	ProductService productService;
	@Autowired
	CustomerService customerService;
	@Autowired
	UserService userService;

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
	
	@RequestMapping("/list/{page}")
	public String listInvoice(Model model,@ModelAttribute("searchForm") Invoice invoice,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		invoice.setInvoiceType(Constant.GOODS_ISSUED);
		List<Invoice> list = invoiceService.getAll(invoice, paging);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", paging);
		if(session.getAttribute(Constant.MSG_ERROR) != null) {
			System.out.println(session.getAttribute(Constant.MSG_ERROR));
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		if(session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		return "goods-issue-list";
	}
	
	@RequestMapping(value = {"/add/{page}"})
	public String addInvoice(Model model, @PathVariable("page") int page ,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Product> list = productService.getAll(null, paging);
		model.addAttribute("pageInfo", paging);
		model.addAttribute("listProduct", list);
		model.addAttribute("title", "Bán hàng");
		model.addAttribute("submitForm", new Invoice(Constant.GOODS_ISSUED));
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
		return "goods-issue-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewInvoice(Model model,@PathVariable("id") int id) {
		Invoice invoice = invoiceService.findById(id);
		List<InvoiceDetail> listInvoiceDetails = 	invoiceDetailDAO.getInvoiceDetailByInvoice(id);
		invoice.setListInvoice(listInvoiceDetails);
		model.addAttribute("title", "Xem chi tiết ");
		model.addAttribute("submitForm", invoice);
		model.addAttribute("invoiceIssued", invoice);
		model.addAttribute("viewOnly", true);
		innitSelect(model);
		return "goods-issue-action";
	}

 
	public void innitSelect(Model model) {
		List<CustomerDTO> listCustomer = customerService.getAll(null, null);
		Collections.sort(listCustomer,new Comparator<CustomerDTO>() {
			public int compare(CustomerDTO o1, CustomerDTO o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("listCustomer", listCustomer);
	}
	
	@GetMapping(value = {"/order"})
	public String orderProduct(@RequestParam("id") long id, HttpServletRequest request, @RequestParam("customerId") long customerId) {
		HttpSession session = request.getSession();
		Invoice invoice = (Invoice) session.getAttribute("invoiceIssued");
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
				invoice.setObjectId(customerId);
				invoice.setObjectType(Constant.OBJECT_TYPE_CUSTOMER);
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
				invoice.setObjectId(customerId);
				invoice.setObjectType(Constant.OBJECT_TYPE_CUSTOMER);
				invoice.calTotalPrice();
			}
			session.setAttribute(Constant.MSG_SUCCESS, "Đặt thành công");
		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute(Constant.MSG_ERROR, "Đặt thất bại");
		}
		
		session.setAttribute("invoiceIssued", invoice);
		return "redirect:/goods-issue/add/1";
	}
	@GetMapping(value = {"/remove/{id}"})
	public String removeItem(@PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Invoice orders = (Invoice) session.getAttribute("invoiceIssued");
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
 
		session.setAttribute("invoiceIssued", orders);
		return "redirect:/goods-issue/add/1";
	}
	@GetMapping(value = {"/cancel"})
	public String cancelInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			Invoice orders = (Invoice) session.getAttribute("invoiceIssued");
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
		return "redirect:/goods-issue/add/1";
	}
	
	@RequestMapping(value = {"/pay"})
	public String payInvoice(HttpServletRequest request, @ModelAttribute("submitForm") Invoice submitForm) {
		HttpSession session = request.getSession();
		Invoice orders = (Invoice) session.getAttribute("invoiceIssued");
		User user = (User) session.getAttribute(Constant.USER_INFO);
		try {
			if(orders != null) {
				orders.setActiveFlag(1);
				orders.setInvoiceType(Constant.GOODS_ISSUED);
				orders.setObjectId(submitForm.getObjectId());
				orders.setObjectType(Constant.OBJECT_TYPE_CUSTOMER);
				orders.setUser(user);
				CustomerDTO customer = customerService.findById(submitForm.getObjectId());
				orders.setObjectName(customer.getName());
				long id = invoiceService.add(orders);
				boolean flag = true;
				for(InvoiceDetail item : orders.getListInvoice()) {
					Invoice invoice = invoiceService.findById(id);
					if(item.getProduct() != null ) {
						Product product = productService.findById(item.getProduct().getId());
						if(product.getQuantity() < item.getQuantity()) {
							session.setAttribute(Constant.MSG_ERROR, "Số lượng hiện có ít hơn số lượng bán ra ");
							flag = false;
						}else{
							product.setQuantity(product.getQuantity() - item.getQuantity());
						}
						productService.update(product);
					}		 
					item.setInvoice(invoice);
					invoiceDetailService.add(item);
				}
				if(flag) {
					session.removeAttribute("invoiceIssued");
					session.setAttribute(Constant.MSG_SUCCESS, "Thanh toán thành công");
				}
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Thanh toán thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Thanh toán thất bại");
		}
		return "redirect:/goods-issue/add/1";
	}
	
	@RequestMapping("/report/list")
	public String reportList(Model model, @ModelAttribute("searchForm") SearchReportForm searchReportForm 
			,HttpSession session) {
		List<ReportValue> reportValues = new ArrayList<ReportValue>();
		searchReportForm.setInvoiceType(Constant.GOODS_ISSUED);
		List<Map<String, Object>> maps = invoiceDAO.report(searchReportForm);
		BigDecimal  totalprice = BigDecimal.ZERO;
		for(Map<String, Object> map : maps) {
			BigDecimal price = (BigDecimal) map.get("price");
			String name = String.valueOf(map.get("name"));
			Object userId =   map.get("userId");
			reportValues.add(new ReportValue( name , price, userId));
			totalprice = totalprice.add(price);
		}
		model.addAttribute("list", reportValues);
		model.addAttribute("totalprice", totalprice);
 
		User user = new User();
		user.setRole(Constant.ROLE_USER);
		List<User> lstUsers = userService.getAll(user, null);
		Collections.sort(lstUsers,new Comparator<User>() {
			public int compare(User o1, User o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("lstUsers", lstUsers);
		
		return "goods-issue-report";
	}
	
 
	
}
