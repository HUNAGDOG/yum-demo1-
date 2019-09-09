package com.imooc.application;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.imooc.controller.interceptor.MiniInterceptor;

@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/**")
		 .addResourceLocations("classpath:/META-INF/resources/")
		 .addResourceLocations("file:C:/imooc_video_dev/");
	}
	@Bean
	public MiniInterceptor miniInterceptor() {
		return new MiniInterceptor();
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
		.addPathPatterns("/bgm/**").addPathPatterns("/video/upload","/video/saveComment").excludePathPatterns("/user/querypublisher");
		super.addInterceptors(registry);
	}
	@Bean(initMethod = "init")
	public ZKCuratorClient zkCuratorClient() {
		
		return new ZKCuratorClient();
	}
	
	
	

}
