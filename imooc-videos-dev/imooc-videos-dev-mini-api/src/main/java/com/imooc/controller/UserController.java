package com.imooc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;
import com.imooc.pojo.vo.PublisherVideo;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "用户相关业务接口", tags = "这是实现用户业务的controller")
@RequestMapping("/user")
public class UserController extends BasicController {
	@Autowired
	UserService userService;
	
	@PostMapping("/uploadFace")
	@ResponseBody
	@ApiOperation(value = "用户上传头像",notes = "用户上传头像")
	@ApiImplicitParam(name = "userId",value = "用户id",dataType = "String",paramType = "query",required = true)
	public IMoocJSONResult uploadFace(String userId,@RequestParam("file")MultipartFile[] files) throws Exception {
		String fileSpace = "C:/imooc_video_dev";
		//保存到数据库中的路径
		String uploadpathDB = "/"+userId+"/face";
		InputStream inputFile;
		FileOutputStream outputFile = null;
		if(StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户id不能为空");
		}
		try {
			if(files != null && files.length>0) {
				String fileName = files[0].getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)) {
					//上传文件的最终路径
					String finalPath = fileSpace+uploadpathDB+"/"+fileName;
					//数据库保存的路径
					uploadpathDB+=("/"+fileName);
					File outFile = new File(finalPath);
					if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						//创建文件夹
						outFile.getParentFile().mkdirs();					
					}
					outputFile = new FileOutputStream(outFile);
					inputFile = files[0].getInputStream();
					IOUtils.copy(inputFile, outputFile);
					
				}else {
					return IMoocJSONResult.errorMsg("文件上传出错");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(outputFile != null) {
				outputFile.flush();
				outputFile.close();
			}
		}
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadpathDB);
		userService.updateUserInfo(user);
		
		
		return IMoocJSONResult.ok(uploadpathDB);
	}
	
	@PostMapping("/query")
	@ResponseBody
	@ApiOperation(value = "显示用户信息",notes = "显示信息")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId",value = "用户id",dataType = "String",paramType = "query",required = true),
		@ApiImplicitParam(name = "fanId",value = "粉丝id",dataType = "String",paramType = "query",required = true)
	})
	public IMoocJSONResult query(String userId,String fanId){
		Users user = userService.queryUser(userId);
		boolean flag = userService.isFollowed(userId, fanId);
		UsersVO userVo = new UsersVO();		
		BeanUtils.copyProperties(user, userVo);
		userVo.setFollowed(flag);
		
		return IMoocJSONResult.ok(userVo);
		
	}
	@PostMapping("/querypublisher")
	@ResponseBody
	public IMoocJSONResult querypublisher(String loginUserId ,String videoId ,String publisherUserId){
		if(StringUtils.isBlank(publisherUserId)) {
			return IMoocJSONResult.errorException("");
		}
		Users user = userService.queryUser(publisherUserId);
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(user, userVO);	
		boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);
		PublisherVideo publisherVideo = new PublisherVideo();
		publisherVideo.setUsersVO(userVO);
		publisherVideo.setUserLikeVideo(userLikeVideo);		
		return IMoocJSONResult.ok(publisherVideo);
		
	}
	@PostMapping("/beyourfans")
	@ResponseBody
	public IMoocJSONResult beyourfans(String userId,String fanId) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
			return IMoocJSONResult.errorMsg("");
		}
		userService.saveUerFanRelation(userId, fanId);
		return IMoocJSONResult.ok("已关注");
	}
	@PostMapping("/nobeyourfans")
	@ResponseBody
	public IMoocJSONResult nobeyourfans(String userId,String fanId) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
			return IMoocJSONResult.errorMsg("");
		}
		userService.deleteUerFanRelation(userId, fanId);
		return IMoocJSONResult.ok("已取消");
	}
	@PostMapping("/reportUser")
	@ResponseBody
	public IMoocJSONResult reportUser(@RequestBody UsersReport usersReport) {
		userService.reportUser(usersReport);
		return IMoocJSONResult.errorMsg("举报成功");
	}

}
