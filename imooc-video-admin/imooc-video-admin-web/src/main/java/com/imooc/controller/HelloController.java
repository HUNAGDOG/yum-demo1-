package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping	("hello")
	public String hello() {
		return "hello";
	}
	@GetMapping("center")
	public String center() {
		return "center";
	}

}
