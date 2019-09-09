package com.imooc.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.pojo.AdminUser;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;

@Controller
@RequestMapping("users")
public class UsersController {
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("login")
	@ResponseBody
	public IMoocJSONResult login(String username , String password ,
			HttpServletRequest request , HttpServletResponse response) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return IMoocJSONResult.errorMsg("用户名或密码不能为空");
		}else if (username.equals("lee") && password.equals("lee")) {
			String token = UUID.randomUUID().toString();
			AdminUser user = new AdminUser(username,password,token);
			request.getSession().setAttribute("sessionUser", user);	
			return IMoocJSONResult.ok();
		}
		return IMoocJSONResult.errorMsg("请重新登陆");
	}

	@GetMapping("logout")
	@ResponseBody
	public String logout(HttpServletRequest request , HttpServletResponse response) {
		request.getSession().removeAttribute("sessionUser");
		return "login";
	}
	@GetMapping("/showList")
	public String showList() {
		return "users/usersList";
	}
	@PostMapping("/list")
	@ResponseBody
	public PagedResult list(Users user , Integer page) {
		PagedResult result = userService.queryUsers(user, page == null ? 1 : page , 10);
		return result;
		
	}
}
