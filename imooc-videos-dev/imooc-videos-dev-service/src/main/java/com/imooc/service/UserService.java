package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;

public interface UserService {
	
	public void updateUserInfo(Users user) ;
	public Users queryUser(String userId);
	public boolean isUserLikeVideo(String userId,String videoId);	
	public void saveUerFanRelation(String userId,String fanId);
	public void deleteUerFanRelation(String userId,String fanId);
	public boolean isFollowed(String userId,String fanId);
	public void reportUser(UsersReport usersReport);
	
	

}
