package com.imooc.application;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class WarApplication extends SpringBootServletInitializer{

	//相当于配置web.xml
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		//web.xml中指向StartApplication.class
		return builder.sources(StartApplication.class);
	}

}
