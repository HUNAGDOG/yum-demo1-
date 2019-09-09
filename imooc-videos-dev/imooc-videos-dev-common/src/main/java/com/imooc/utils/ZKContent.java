package com.imooc.utils;

import org.springframework.stereotype.Component;

@Component
public class ZKContent  {
	private String operType;
	private String path;
	
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	

}
