package com.app.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.app.entity.User;
import com.app.service.UserService;
import com.app.utils.Constant;

public class FilterUser implements HandlerInterceptor{
	@Autowired
	private UserService userService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session  =request.getSession();
		User user = (User) session.getAttribute(Constant.USER_INFO);
		if(user.getRole() != null) {
			if(user.getRole() == Constant.ROLE_USER || user.getRole() == Constant.ROLE_USER ) {
				return true;
			} 
			response.sendRedirect(request.getContextPath() + "/access-denied");
			return false;
		}else {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
