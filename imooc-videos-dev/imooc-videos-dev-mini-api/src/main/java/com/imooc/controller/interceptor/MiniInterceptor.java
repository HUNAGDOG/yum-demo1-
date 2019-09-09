package com.imooc.controller.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;

public class MiniInterceptor implements HandlerInterceptor {
	@Autowired
	public RedisOperator redis;
	
	public static final String USER_REDIS_SESSION="user-redis-session";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userId = request.getHeader("userId");
		String userToken = request.getHeader("uniqueToken");			
		if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(userToken)) {
			String uniqueToken = redis.get(USER_REDIS_SESSION+":"+userId);
			if(StringUtils.isEmpty(uniqueToken)&&StringUtils.isBlank(uniqueToken)) {
				System.out.println("请登录。。。。。。。。");
				returnErrorResponse(response, new IMoocJSONResult().errorTokenMsg("请登录"));
				return false;
				
			}else {
				if(!uniqueToken.equals(userToken)) {
					System.out.println("账号在其他终端登陆");
					returnErrorResponse(response, new IMoocJSONResult().errorTokenMsg("账号在其他手机登陆"));
					return false;
				}
				
			}
			
		}else {
			
			returnErrorResponse(response, new IMoocJSONResult().errorTokenMsg("请先登陆"));
			return false;
		}
		
		return true;
		
		
	}
	public void returnErrorResponse(HttpServletResponse response,IMoocJSONResult result) throws IOException ,UnsupportedEncodingException {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			out = response.getOutputStream();
			out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
			out.flush();
			
		}finally {
			if(out!=null) {
				out.close();
			}
			
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
