package com.app.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.dao.InvoiceDAO;
import com.app.entity.Invoice;
import com.app.entity.User;
import com.app.service.UserService;
import com.app.utils.Constant;
import com.app.validator.LoginValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	@Autowired
	UserService userService;
	@Autowired
	LoginValidator loginValidator;
	
	@Autowired
	InvoiceDAO<Invoice> invoiceDAO;
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		if(dataBinder.getTarget().getClass() == User.class) {
			dataBinder.setValidator(loginValidator);
		}
	}
	
	@GetMapping(value = {"/","/index","/home"})
	public String index(HttpSession session, Model model) {
		initBarChart(model);
		return "chart";
	}
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new User());
		return "login";
	}
	@GetMapping("/access-denied")
	public String accessDenied(Model model) {
		return "access-denied";
	}
	
	@PostMapping("/processLogin")
	public String processLogin(Model model,@ModelAttribute("loginForm") @Validated User user, BindingResult result,HttpSession 
			session) {
		if(result.hasErrors()) {
			return "login";
		}
		List<User> lstUser = userService.getAllByProperty("username", user.getUsername());
		if(lstUser != null && lstUser.size() > 0){
			User userInfo = lstUser.get(0);
			session.setAttribute(Constant.USER_INFO, userInfo);
		}
		return "redirect:/index";
	}
	@GetMapping("/logout")
	public String login(HttpSession session) {
		session.removeAttribute(Constant.USER_INFO);
		return "redirect:/index";
	}
	
//	@GetMapping("/statistics/chart")
//	public String statistics(HttpSession session, Model model) {
//		initBarChart(model);
//		return "chart";
//	}
	
	private void initBarChart(Model model) {
		  
	 
		try {
			String barcharJsonReceipt = getJsonReceipt();
			String barcharJsonIssued = getJsonIssued();
			 
			System.out.println(barcharJsonReceipt);
			System.out.println(barcharJsonIssued);
			model.addAttribute("barcharJsonReceipt", barcharJsonReceipt);
			model.addAttribute("barcharJsonIssued", barcharJsonIssued);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getJsonIssued() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 
		 Map<Integer,Object> mapReceipt = new  HashMap(); 	
	
		 for(Integer i = 1 ; i <= 12 ;i++) {
			 mapReceipt.put(i, 0);		 
		 }
		 //theo thang
		 List<Map<String, Object>> maps = invoiceDAO.getBarChartByMonth(calendar.get(Calendar.YEAR), Constant.GOODS_ISSUED);
		 for(Map<String,Object> item :	maps) {
			 mapReceipt.put(Integer.parseInt(item.get("month").toString()), item.get("totalPrice"));
		 }
		 List<Map<String,Object>> lstObjectPrice = new ArrayList<Map<String,Object>>();
	 
		 for(Integer key : mapReceipt.keySet()) {
			 Map<String,Object> receipt = new  HashMap();
			 receipt.put("label", key);
			 receipt.put("value", mapReceipt.get(key));
			 lstObjectPrice.add(receipt);
		 }
		 String jsonPriceMonth = mapper.writeValueAsString(lstObjectPrice);		
		 return jsonPriceMonth;
	}
	
	public String getJsonReceipt() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 
		 Map<Integer,Object> mapReceipt = new  HashMap(); 	
	
		 for(Integer i = 1 ; i <= 12 ;i++) {
			 mapReceipt.put(i, 0);		 
		 }
		 //theo thang
		 List<Map<String, Object>> maps = invoiceDAO.getBarChartByMonth(calendar.get(Calendar.YEAR), Constant.GOODS_RECEIPT);
		 for(Map<String,Object> item :	maps) {
			 mapReceipt.put(Integer.parseInt(item.get("month").toString()), item.get("totalPrice"));
		 }
		 List<Map<String,Object>> lstObjectPrice = new ArrayList<Map<String,Object>>();
	 
		 for(Integer key : mapReceipt.keySet()) {
			 Map<String,Object> receipt = new  HashMap();
			 receipt.put("label", key);
			 receipt.put("value", mapReceipt.get(key));
			 lstObjectPrice.add(receipt);
		 }
		 String jsonPriceMonth = mapper.writeValueAsString(lstObjectPrice);		
		 return jsonPriceMonth;
	}
 
}
