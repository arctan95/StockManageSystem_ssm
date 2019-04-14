package com.tan.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tan.entity.User;
import com.tan.service.UserService;
import com.tan.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource//默认按照名称进行装配
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request){
		HttpSession session=request.getSession(); 
		User currentUser=userService.checkLogin(user.getUserName(), user.getPassword());
		

		if(StringUtil.isEmpty(user.getUserName())||StringUtil.isEmpty(user.getPassword())){
			request.setAttribute("error", "用户名或密码为空！");
			return "forward:/index.jsp";
		}
		
		if(currentUser!=null&&request.getParameter("imageCode").equals(session.getAttribute("sRand"))){
			session.setAttribute("currentUser", currentUser);
			return "main";
		}else if(currentUser!=null&&!request.getParameter("imageCode").equals(session.getAttribute("sRand"))){
				request.setAttribute("error", "验证码错误！");
		}else{
			request.setAttribute("error", "用户名或密码错误！");
		}
		return "forward:/index.jsp";
	}
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest request){
		HttpSession session=request.getSession(); 
		session.removeAttribute("currentUser");
			return "redirect:/index.jsp";
		}
	}

