package com.imooc.controller;


import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.RegistService;

import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


@Controller
@Api(value = "这是用户注册登陆的接口", tags = {"注册和登陆的controller"})

public class RegistController extends BasicController {
	@Autowired
	private RegistService registService;
	
	@PostMapping("/regist")
	@ResponseBody
	@ApiOperation(value = "用户注册",notes = "用户注册的接口")
	public IMoocJSONResult regist(@RequestBody Users user) throws Exception {
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())) {
			return IMoocJSONResult.errorMsg("用户名和密码不能为空");
		}
		if(!registService.queryUserNmaeIsExit(user.getUsername())) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCounts(0);
			user.setFollowCounts(0);
			user.setReceiveLikeCounts(0);
			registService.saveUser(user);
			
		}else {
			return IMoocJSONResult.errorMsg("用户名已经存在");
		}
		user.setPassword("");
		UsersVO userVO = setuserRedisSessionToken(user);
		return IMoocJSONResult.ok(userVO);
	}
	public UsersVO setuserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION+":"+userModel.getId(), uniqueToken, 1000*60*30);
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userModel, userVO);
		userVO.setUniqueToken(uniqueToken);
		return userVO;
	}
	
	@PostMapping("/login")
	@ResponseBody
	@ApiOperation(value = "用户登陆接口",tags = {"用户登陆的接口"})
    public IMoocJSONResult login(@RequestBody Users user) throws Exception {
		String username = user.getUsername();
		String passowrd = MD5Utils.getMD5Str(user.getPassword());
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())) {
			return IMoocJSONResult.errorMsg("用户名和密码不能为空");
		}
		Users users = registService.queryUser(username, passowrd);
		if(users ==null) {
			return IMoocJSONResult.errorMsg("用户名或密码不正确");
		}else {
			users.setPassword("");
			UsersVO userVO = setuserRedisSessionToken(users);
			return IMoocJSONResult.ok(userVO);
		}
    }
	@PostMapping("/logout")
	@ApiOperation(value = "用户注销接口", tags = "这是用户注销接口")
	@ApiImplicitParam(name = "userId", value = "用户Id",required = true,dataType = "String",paramType = "query")
	@ResponseBody
	public IMoocJSONResult logout(String userId) {
		redis.del(USER_REDIS_SESSION+":"+userId);
		
		return IMoocJSONResult.ok("成功");
		
	}
}
