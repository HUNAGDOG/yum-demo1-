package com.imooc.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.UsersExample;
import com.imooc.pojo.UsersExample.Criteria;
import com.imooc.service.UserService;
import com.imooc.utils.PagedResult;

@Service
public class userServiceImpl implements UserService {

	@Autowired
	UsersMapper userMapper;
	@Override
	public PagedResult queryUsers(Users user, Integer page , Integer pageSize) {
		String username = "";
		String nickname = "";
		if(user != null) {
			username = user.getUsername();
			nickname = user.getNickname();
		}
		PageHelper.startPage(page, pageSize);
		UsersExample example = new UsersExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(username)) {
			criteria.andUsernameLike("%" + username + "%");
		}
		if(StringUtils.isNotBlank(nickname)) {
			criteria.andNicknameLike("%" + nickname + "%");
		}
		List<Users> list = userMapper.selectByExample(example);
		PageInfo<Users> info = new PageInfo<Users>(list);
		PagedResult result = new PagedResult();

		result.setPage(page);
		result.setRecords(info.getTotal());
		result.setRows(list);
		result.setTotal(info.getPages());
		return result;
		
	}

}
