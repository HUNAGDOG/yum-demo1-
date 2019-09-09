package com.imooc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.BGMOperatorTypeEnum;
import com.imooc.mapper.BgmMapper;
import com.imooc.mapper.UsersReportMapperCustom;
import com.imooc.mapper.VideosMapper;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.BgmExample;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.Reports;
import com.imooc.service.VideoService;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.PagedResult;
import com.imooc.utils.ZKContent;
import com.imooc.utils.ZKCurator;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	BgmMapper bgmMapper;
	@Autowired
	Sid sid;
	
	@Autowired
	ZKCurator zkCurator;
	@Autowired
	UsersReportMapperCustom usersReportMapperCustom;

	@Autowired
	VideosMapper videosMapper;
	@Override
	public void addBgm(Bgm bgm) {
		String bgmId = sid.nextShort();
		bgm.setId(bgmId);
		bgmMapper.insert(bgm);			
		Map<String, String> map = new HashMap<String, String>();
		map.put("zoperType", BGMOperatorTypeEnum.ADD.type);
		map.put("zpath", bgm.getPath());
		zkCurator.sendBgmOperation(bgmId, JsonUtils.objectToJson(map));

	}
	@Override
	public void deleteBgm(String id) {
		Bgm bgm = bgmMapper.selectByPrimaryKey(id);
		bgmMapper.deleteByPrimaryKey(id);		
		Map<String, String> map = new HashMap<String, String>();
		map.put("zoperType", BGMOperatorTypeEnum.ADD.type);
		map.put("zpath", bgm.getPath());
		zkCurator.sendBgmOperation(id, JsonUtils.objectToJson(map));
		
	}

	@Override
	public PagedResult queryBgmList(Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		BgmExample example = new BgmExample();
		List<Bgm> list = bgmMapper.selectByExample(example);
		PageInfo<Bgm> info = new PageInfo<Bgm>(list);
		PagedResult result = new PagedResult();
		result.setPage(page);
		result.setRecords(info.getTotal());
		result.setTotal(info.getPages());
		result.setRows(list);
		

		return result;
	}



	public PagedResult queryReportList(Integer page, Integer pageSize) {

		PageHelper.startPage(page, pageSize);

		List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();

		PageInfo<Reports> pageList = new PageInfo<Reports>(reportsList);

		PagedResult grid = new PagedResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(reportsList);
		grid.setPage(page);
		grid.setRecords(pageList.getTotal());

		return grid;
	}

	
	@Override
	public void updateVideoStatus(String videoId, Integer status) {
		
		Videos video = new Videos();
		video.setId(videoId);
		video.setStatus(status);
		videosMapper.updateByPrimaryKeySelective(video);
	}

}
