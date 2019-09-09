package com.imooc.serviceimpl;

import java.util.Date;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.CommentsMapper;
import com.imooc.mapper.CommentsMapperCustom;
import com.imooc.mapper.SearchRecordsMapper;
import com.imooc.mapper.UsersLikeVideosMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.VideosMapper;
import com.imooc.mapper.VideosMapperCustom;
import com.imooc.pojo.Comments;
import com.imooc.pojo.SearchRecords;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.CommentsVO;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imooc.utils.TimeAgoUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	Sid sid;	
	@Autowired
	VideosMapper videoMapper;
	@Autowired
	VideosMapperCustom videosMapperCustom;
	@Autowired
	SearchRecordsMapper searchRecordsMapper;
	@Autowired
	UsersLikeVideosMapper usersLikeVideoMapper;
	@Autowired
	UsersMapper userMapper;
	@Autowired
	CommentsMapper commentsMapper;
	@Autowired
	CommentsMapperCustom commentsMapperCustom;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveVideo(Videos video) {
		String id = sid.nextShort();
		video.setId(id);
		videoMapper.insertSelective(video);
		return id;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateConverPath(String videoId, String converPath) {
		Videos videos = new Videos();
		videos.setId(videoId);
		videos.setCoverPath(converPath);
		videoMapper.updateByPrimaryKeySelective(videos);
		
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public PagedResult getAllVideos(Videos video , Integer isSaveRecord ,Integer page, Integer pageSize) {
		String desc = video.getVideoDesc();
		String userId = video.getUserId();
		if(isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords records = new SearchRecords();
			records.setId(sid.nextShort());
			records.setContent(desc);
			searchRecordsMapper.insert(records);	
		}
		PageHelper.startPage(page, pageSize);
		List<VideosVO> queryAllVideos = videosMapperCustom.queryAllVideos(desc,userId);
		PageInfo<VideosVO> pageInfo = new PageInfo<VideosVO>(queryAllVideos);
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageInfo.getPages());
		pagedResult.setRecords(pageInfo.getTotal());
		pagedResult.setRows(queryAllVideos);
		return pagedResult;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getHotWords() {
		
		return searchRecordsMapper.getHotWords();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
		//1 保存用户与视频的点赞关系表
		UsersLikeVideos ulv = new UsersLikeVideos();
		ulv.setId(sid.nextShort());
		ulv.setUserId(userId);
		ulv.setVideoId(videoId);
		usersLikeVideoMapper.insert(ulv);
		//2 视频喜欢数量累加
		videosMapperCustom.addVideoLikeCount(videoId);
		
		//3 用户受喜欢数量的累加
		userMapper.addReceiveLikeCount(videoCreaterId);
		
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userunLikeVideo(String userId, String videoId, String videoCreaterId) {
		        //1 删除用户与视频的点赞关系表
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		usersLikeVideoMapper.deleteByExample(example);
		// 2 视频喜欢数量累减
		videosMapperCustom.reduceVideoLikeCount(videoId);

		// 3 用户受喜欢数量的累减
		userMapper.reduceReceiveLikeCount(videoCreaterId);
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getMyLikeVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);
		PageInfo<VideosVO> pageInfo = new PageInfo<VideosVO>();
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageInfo.getPages());
		pagedResult.setRecords(pageInfo.getTotal());
		pagedResult.setRows(list);
		return pagedResult;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getMyFollowVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);
		PageInfo<VideosVO> pageInfo = new PageInfo<VideosVO>();
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageInfo.getPages());
		pagedResult.setRecords(pageInfo.getTotal());
		pagedResult.setRows(list);
	
		return pagedResult;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveComment(Comments comment) {
		comment.setId(sid.nextShort());
		comment.setCreateTime(new Date());
		commentsMapper.insert(comment);
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);		
		List<CommentsVO> list = commentsMapperCustom.queryComments(videoId);
		
		for(CommentsVO c : list) {
			c.setTimeAgoStr(TimeAgoUtils.format(c.getCreateTime()));
		}
		PageInfo<CommentsVO> pageInfo = new PageInfo<CommentsVO>(list);
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageInfo.getPages());
		
		pagedResult.setRows(list);
		pagedResult.setRecords(pageInfo.getTotal());
		
		return pagedResult;
	}

	
	

}
