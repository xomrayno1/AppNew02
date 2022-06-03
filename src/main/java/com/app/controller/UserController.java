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
import com.app.entity.User;
import com.app.service.UserService;
import com.app.utils.Constant;
import com.app.validator.UserValidator;

@Controller
@RequestMapping("/manage/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserValidator userValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if(dataBinder.getTarget().getClass() == User.class) {
			dataBinder.setValidator(userValidator);
		}
	}
	
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/manage/user/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listUser(Model model,@ModelAttribute("searchForm") User user,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<User> list = userService.getAll(user, paging);
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
		return "user-list";
	}
	
	@GetMapping(value = {"/add"})
	public String addUser(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new User());
		model.addAttribute("viewOnly", false);
		return "user-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewUser(Model model,@PathVariable("id") Long id) {
		User user = userService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", user);
		model.addAttribute("viewOnly", true);
		return "user-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editUser(Model model,@PathVariable("id") Long id) {
		User user = userService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", user);
		model.addAttribute("viewOnly", false);
		return "user-action";
	}
	@PostMapping(value = {"/save"})
	public String saveUser(Model model,@ModelAttribute("submitForm") @Validated User user 
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			if(user.getId() != null) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			model.addAttribute("submitForm", user);
			return "user-action";
		}
		if(user.getId() != null) {
			try {
				userService.update(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				userService.add(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/manage/user/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteUser(Model model,@PathVariable("id") Long id,HttpSession session) {
		User user = userService.findById(id);
		try {
			userService.delete(user);
			session.setAttribute(Constant.MSG_SUCCESS, "Xóa thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xóa thất bại");
		}
		return "redirect:/manage/user/list/1";
	}
}
