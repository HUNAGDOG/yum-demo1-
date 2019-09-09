package com.imooc.serviceimpl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.RegistService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class RegistServiceImpl implements RegistService {
    @Autowired
	private UsersMapper userMapper;
    @Autowired
    private Sid sid;
     //保存用户信息
    @Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		// TODO Auto-generated method stub
		user.setId(sid.nextShort());
		userMapper.insert(user);
		
	}
    //查询用户是否存在
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUserNmaeIsExit(String username) {
		// TODO Auto-generated method stub
		Users user = new Users();
		user.setUsername(username);
		Users users = userMapper.selectOne(user);
		
		return users == null ? false:true;
	}
      //用户登陆校验
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUser(String username,String password) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", password);
		Users users = userMapper.selectOneByExample(userExample);
		return users;
		
		
			
		
	}
	

}
