package com.imooc.service;

import java.util.List;

import com.imooc.pojo.Comments;
import com.imooc.pojo.Videos;
import com.imooc.utils.PagedResult;

public interface VideoService {
	
	public String saveVideo(Videos video) ;
	public void updateConverPath(String userId, String converPath);
	public PagedResult getAllVideos(Videos video , Integer isSaveRecord ,Integer page,Integer pageSize);
	public List<String> getHotWords();
	public void userLikeVideo(String userId,String videoId,String videoCreaterId);
	public void userunLikeVideo(String userId,String videoId,String videoCreaterId);
	public PagedResult getMyLikeVideos(String userId,Integer page, Integer pageSize);
	public PagedResult getMyFollowVideos(String userId,Integer page, Integer pageSize);
	public void saveComment(Comments comment);
	public PagedResult getAllComments(String videoId,Integer page,Integer pageSize);
	

}
