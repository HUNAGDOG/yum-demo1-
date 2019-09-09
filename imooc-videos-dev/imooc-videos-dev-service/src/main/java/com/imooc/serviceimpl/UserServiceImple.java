package com.imooc.serviceimpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.mapper.UsersFansMapper;
import com.imooc.mapper.UsersLikeVideosMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.UsersReportMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.UsersFans;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.UsersReport;
import com.imooc.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImple implements UserService {
	@Autowired
	UsersMapper userMapper;
	@Autowired
	UsersLikeVideosMapper usersLikeVideosMapper;
	@Autowired
	UsersFansMapper userFansMapper;
	@Autowired
	Sid sid;
	@Autowired
	UsersReportMapper userReportMapper;

	//更新用户头像信息
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateUserInfo(Users user) {
		
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", user.getId());
		userMapper.updateByExampleSelective(user, userExample);
		
	}

	//显示用户信息
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUser(String userId) {
		Example  userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", userId);
		Users user = userMapper.selectOneByExample(userExample);
		return user;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isUserLikeVideo(String userId, String videoId) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(videoId) ) {
			return false;
		}
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);
		if(list !=null && list.size()>0) {
			return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUerFanRelation(String userId, String fanId) {


		UsersFans userFans = new UsersFans();
		userFans.setId(sid.nextShort());
		userFans.setFanId(fanId);
		userFans.setUserId(userId);
		userFansMapper.insert(userFans);
		userMapper.addFansCount(userId);
		userMapper.addFollowCount(fanId);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteUerFanRelation(String userId, String fanId) {
		
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fanId);
		userFansMapper.deleteByExample(example);
		userMapper.reduceFansCount(userId);
		userMapper.reduceFollowCount(fanId);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isFollowed(String userId, String fanId) {
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fanId);
		List<UsersFans> list = userFansMapper.selectByExample(example);
		if(list != null && list.size()>0 && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void reportUser(UsersReport usersReport) {
		usersReport.setId(sid.nextShort());
		usersReport.setCreateDate(new Date());
		userReportMapper.insert(usersReport);
		
	}

	
	

}
