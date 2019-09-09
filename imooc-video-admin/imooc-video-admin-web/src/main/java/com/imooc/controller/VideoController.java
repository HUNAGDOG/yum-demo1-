package com.imooc.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.pojo.Bgm;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;

@Controller
@RequestMapping("/video")
public class VideoController {
	@Autowired
	private VideoService videoService;
	@Value("${FILE_SPACE}")
	private String FILE_SPACE;

	@GetMapping("/showAddBgm")
	public String showAddBgm() {
		return "/video/addBgm";
	}
	@GetMapping("/showBgmList")
	public String showBgmList() {
		return "/video/bgmList";
	}
	@GetMapping("/showReportList")
	public String showReportList() {
		return "video/reportList";
	}
	@PostMapping("reportList")
	@ResponseBody
	public PagedResult reportList(int page) {
		PagedResult result = videoService.queryReportList(page,10);
		return result;
	}
	@PostMapping("/forbidVideo")
	@ResponseBody
	public IMoocJSONResult forbidVideo(String videoId) {
		
		videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
		return IMoocJSONResult.ok();
	}
	@PostMapping("/queryBgmList")
	@ResponseBody
	public PagedResult queryBgmList(Integer page) {
		return videoService.queryBgmList(page, 5);
	}
	@PostMapping("/addBgm")
	@ResponseBody
	public IMoocJSONResult addBgm(Bgm bgm) {
		videoService.addBgm(bgm);
		return IMoocJSONResult.ok();
		
	}
	@PostMapping("/delBgm")
	@ResponseBody
	public IMoocJSONResult delBgm(String bgmId) {
		videoService.deleteBgm(bgmId);
		System.out.println("删除成功");
		return IMoocJSONResult.ok();
		
	}
	
	@PostMapping("/bgmUpload")
	@ResponseBody
	public IMoocJSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {
		//String fileSpace = "F:"+ File.separator +"imooc_video_dev"+File.separator+"mvcbgm";
		String filePathDB = File.separator +"bgm";
	    FileOutputStream outputFile = null;
	    InputStream inputFile = null;
	    try {
			if(files != null || files.length>0) {
				String fileName = files[0].getOriginalFilename();
				System.out.println(fileName);
				if(StringUtils.isNotBlank(fileName)) {
					String finalPath = FILE_SPACE + filePathDB + File.separator +fileName;
					System.out.println(finalPath);
					filePathDB+=(File.separator + fileName);
					
					File outFile = new File(finalPath);
					if(outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()) {
						outFile.getParentFile().mkdirs();
						
					}
					outputFile = new FileOutputStream(outFile); 
					inputFile = files[0].getInputStream();
					IOUtils.copy(inputFile, outputFile);
					
				}
			}else {
				IMoocJSONResult.errorMsg("上传出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
			IMoocJSONResult.errorMsg("出错了");
			
		} finally {
			if(outputFile != null) {				
				outputFile.flush();
				outputFile.close();			
				
			}
		}
		return IMoocJSONResult.ok(filePathDB);
	}
	
}
