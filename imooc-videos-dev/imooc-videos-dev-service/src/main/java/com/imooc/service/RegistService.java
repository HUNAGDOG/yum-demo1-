package com.imooc.service;

import com.imooc.pojo.Users;

public interface RegistService {
	
	public boolean queryUserNmaeIsExit(String username);
	public void saveUser(Users user);
	public Users queryUser(String ussername,String password);
	

}
