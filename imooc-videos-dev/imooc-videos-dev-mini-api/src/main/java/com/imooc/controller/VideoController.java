package com.imooc.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.PostRemove;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.Comments;
import com.imooc.pojo.Videos;
import com.imooc.service.BgmService;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MergeMp3Video;
import com.imooc.utils.PagedResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/video")
@Api(value = "用户与视频有关的接口", tags = {"这是视频业务的controller"})
public class VideoController extends BasicController {
	@Autowired
	BgmService bgmService;
	@Autowired
	VideoService videoService;
	
	@PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
	@ResponseBody
	@ApiOperation(value = "用户上传视频的接口",notes = "上传视频")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId" , value = "用户id" , required = true ,
				dataType = "String" , paramType = "form"),
		@ApiImplicitParam(name = "bgmId" , value = "背景音乐id" , required = false ,
		        dataType = "String" , paramType = "form"),
		@ApiImplicitParam(name = "videoSeconds" , value = "背景音乐的长度" , required = true ,
		        dataType = "String" , paramType = "form"),
		@ApiImplicitParam(name = "videoHeight" , value = "上传视频的高度" , required = true , 
		        dataType = "String" , paramType = "form"),
		@ApiImplicitParam(name = "videoWidth" , value = "上传视频的宽度" , required = true , 
                dataType = "String" , paramType = "form"),
		@ApiImplicitParam(name = "desc" , value = "上传视频的描述" , required = false , 
                dataType = "String" , paramType = "form"),
		
	})
	public IMoocJSONResult upload(String userId , String bgmId ,double videoSeconds , int videoHeight , 
			                                       int videoWidth , String desc ,@ApiParam(value = "短视频", required = true) MultipartFile file) throws Exception{
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户id不能为空");
			
		}
		String videoSpace = "C:/imooc_video_dev";
		String uploadpathDB = "/"+userId+"/video";
		String coverPathDB = "/"+userId+"/video";
		String finalPath = "";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if(file != null) {
				String videoName = file.getOriginalFilename();
				if(StringUtils.isNotBlank(videoName)) {
				finalPath = videoSpace+uploadpathDB+"/"+videoName;
				
				uploadpathDB+=("/"+videoName);
				File videoFile = new File(finalPath);
				if(videoFile.getParentFile() != null || !videoFile.getParentFile().isDirectory()) {
					videoFile.getParentFile().mkdirs();				
				}
				fileOutputStream = new FileOutputStream(videoFile);
				inputStream = file.getInputStream();
				IOUtils.copy(inputStream, fileOutputStream);
				}else {
					IMoocJSONResult.errorMsg("视频上传出错");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		if(StringUtils.isNotBlank(bgmId)) {
			Bgm bgm = bgmService.selectBgnById(bgmId);
			String bgmPath = videoSpace+bgm.getPath();	
			String vName = UUID.randomUUID().toString();
			MergeMp3Video tool = new MergeMp3Video("C:\\ffmpeg\\bin\\ffmpeg.exe");
			String inputPath = finalPath;
			String outPath1 = videoSpace+"/"+userId+"/video" + "/temp.mp4";
			String outPath2 = videoSpace+"/"+userId+"/video/" +vName+ ".mp4";	
			String outPath3 = videoSpace+"/"+userId+"/video/" +vName+ ".jpg";
			uploadpathDB = "/"+userId+"/video/" + vName + ".mp4";
			coverPathDB = "/"+userId+"/video/" + vName + ".jpg";
			//消音
			tool.convertor1(inputPath, outPath1);		
			//加音乐
			tool.convertor2(outPath1, bgmPath, videoSeconds, outPath2);
			//截图
			tool.convertor3(outPath2, outPath3);			
		}else {
			String vName = UUID.randomUUID().toString();
			MergeMp3Video tool = new MergeMp3Video("C:\\ffmpeg\\bin\\ffmpeg.exe");
			String inputPath = finalPath;
			String outPath = videoSpace+"/"+userId+"/video/" +vName+ ".jpg";
			coverPathDB = "/"+userId+"/video/" + vName + ".jpg";
			tool.convertor3(inputPath, outPath);
			
		}
		Videos video = new Videos();
		video.setAudioId(bgmId);
		video.setUserId(userId);
		video.setVideoWidth(videoWidth);
		video.setVideoSeconds((float)videoSeconds);
		video.setVideoHeight(videoHeight);
		video.setVideoDesc(desc);
		video.setVideoPath(uploadpathDB);
		video.setStatus(VideoStatusEnum.SUCCESS.value);
		video.setCreateTime(new Date());
		video.setCoverPath(coverPathDB);
		String videoId = videoService.saveVideo(video);
		
		return IMoocJSONResult.ok(videoId);
		
	}
	@PostMapping("/showAll")
	@ResponseBody
	public IMoocJSONResult showAll(@RequestBody Videos video , Integer isSaveRecord , Integer page, Integer pageSize ) {
		if(page == null) {
			page = 1;
		}
		if(pageSize == null) {
			pageSize = PAGE_SIZE;
		}
		
		PagedResult videos = videoService.getAllVideos(video,isSaveRecord,page, pageSize);
		return IMoocJSONResult.ok(videos);
		
	}
	@PostMapping("/hot")
	@ResponseBody
	public IMoocJSONResult hot() {
		List<String> hotWords = videoService.getHotWords();
		return IMoocJSONResult.ok(hotWords);
		
	}
	@PostMapping("/userLike")
	@ResponseBody
	public IMoocJSONResult userLike(String userId,String videoId,String videoCreaterId){
		videoService.userLikeVideo(userId, videoId, videoCreaterId);
		return IMoocJSONResult.ok();
	}
	@PostMapping("/userUnLike")
	@ResponseBody
	public IMoocJSONResult userUnLike(String userId,String videoId,String videoCreaterId){
		videoService.userunLikeVideo(userId, videoId, videoCreaterId);
		return IMoocJSONResult.ok();
	}
	@PostMapping("/showMyLike")
	@ResponseBody
	public IMoocJSONResult showMyLike(String userId , Integer page, Integer pageSize) {
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.ok();
		}
		if(page == null) {
			page =1;
		}
		if(pageSize == null) {
			pageSize = 6;
		}
		PagedResult videos = videoService.getMyLikeVideos(userId,page, pageSize);
		
		
		return IMoocJSONResult.ok(videos);
	}
	@PostMapping("/showMyFollow")
	@ResponseBody()
	public IMoocJSONResult showMyFollow(String userId, Integer page) {
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.ok();
		}
		if(page == null) {
			page = 1;
		}
		Integer pageSize = 6;
		PagedResult videos = videoService.getMyFollowVideos(userId,page, pageSize);
		return IMoocJSONResult.ok(videos);
	}
	@PostMapping("/saveComment")
	@ResponseBody
	public IMoocJSONResult saveComment(@RequestBody Comments comment , String fatherCommentId,String toUserId) {
		comment.setFatherCommentId(fatherCommentId);
		comment.setToUserId(toUserId);
		videoService.saveComment(comment);
		return IMoocJSONResult.ok();
		
		
	}
	@PostMapping("getVideoComment")
	@ResponseBody
	public IMoocJSONResult getVideoComment(String videoId,Integer page, Integer pageSize) {
		
		if(StringUtils.isBlank(videoId)) {
			return IMoocJSONResult.ok();
		}
		if(page == null) {
			page = 1;			
		}
		if(pageSize == null) {
			pageSize = 10;
		}
		PagedResult list = videoService.getAllComments(videoId,page,pageSize);
		return IMoocJSONResult.ok(list);
		
	}


}
