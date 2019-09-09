package com.imooc.pojo.vo;

public class PublisherVideo {
	private UsersVO usersVO;
	private boolean userLikeVideo;
	public UsersVO getUsersVO() {
		return usersVO;
	}
	public void setUsersVO(UsersVO usersVO) {
		this.usersVO = usersVO;
	}
	public boolean isUserLikeVideo() {
		return userLikeVideo;
	}
	public void setUserLikeVideo(boolean userLikeVideo) {
		this.userLikeVideo = userLikeVideo;
	}
	

}
