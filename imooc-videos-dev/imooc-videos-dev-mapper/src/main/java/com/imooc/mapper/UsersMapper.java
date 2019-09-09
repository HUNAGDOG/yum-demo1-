package com.imooc.mapper;

import com.imooc.pojo.Users;
import com.imooc.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {
	public void addReceiveLikeCount(String userId);
	public void reduceReceiveLikeCount(String userId);
	public void addFansCount(String userId);
	public void reduceFansCount(String userId);
	public void addFollowCount(String userId);
	public void reduceFollowCount(String userId);
}