package com.imooc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.service.BgmService;
import com.imooc.utils.IMoocJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/bgm")
@Api(value = "背景音乐业务的接口", tags = {"背景音乐的controller"})
public class BgmController {
	@Autowired
	private BgmService bgmService;
	@PostMapping("/list")
	@ResponseBody
	@ApiOperation(value = "获取背景音乐列表",notes = "获取背景音乐列表的接口")
	public IMoocJSONResult list() {
		return IMoocJSONResult.ok(bgmService.queryBgmList());
		
	}
	

}
