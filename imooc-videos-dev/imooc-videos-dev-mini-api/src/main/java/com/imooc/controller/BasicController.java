package com.imooc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.imooc.utils.RedisOperator;

@Controller
public class BasicController {
	
	@Autowired
	public RedisOperator redis;
	
	public static final String USER_REDIS_SESSION="user-redis-session";
	
	public static final Integer PAGE_SIZE = 5;
	

}
