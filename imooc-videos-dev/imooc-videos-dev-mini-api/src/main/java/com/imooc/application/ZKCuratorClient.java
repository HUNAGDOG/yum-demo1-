package com.imooc.application;


import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imooc.enums.BGMOperatorTypeEnum;
import com.imooc.pojo.Bgm;

import com.imooc.service.BgmService;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.ZKContent;

@Component
public class ZKCuratorClient {
	@Autowired
	private ResourceConfig resourceConfig;
	@Autowired
	private BgmService bgmService ;
	private CuratorFramework client;
	static final Logger log = LoggerFactory.getLogger(ZKCuratorClient.class);
	
	//public static final String ZOOKEEPER_SERVER = "192.168.3.128:2181";
	public void init() {
		if(client != null) {
			return;
		}
		//创建策略
	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
	//创建zk客户端
	System.out.println(resourceConfig.getZookeeperServer());
	 client = CuratorFrameworkFactory.builder()
			 .connectString(resourceConfig.getZookeeperServer())
			 .sessionTimeoutMs(1000)
			 .retryPolicy(retryPolicy).namespace("admin").build();
	 client.start();
	 try {
		
		 addChildWatch("/bgm");
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	 }
	public void addChildWatch(String nodePath) throws Exception {
		PathChildrenCache cache = new PathChildrenCache(client,nodePath,true);
		cache.start();
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
			if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
				log.info("监听到事件");
				
				/*
				 * String[] arrPath = path.split("/"); String bgmId = arrPath[arrPath.length-1];
				 * Bgm bgm = bgmService.selectBgnById(bgmId); if(bgm == null) { return; }
				 * //背景音乐的相对路径 String songPath = bgm.getPath();
				 */
				String path = event.getData().getPath();
				String operatorJson = new String(event.getData().getData());
				log.info(operatorJson);				
				Map<String, String> map = JsonUtils.jsonToPojo(operatorJson, Map.class);		
				String zoperType = map.get("zoperType");
				String zpath = map.get("zpath");
				
				//保存的本地路径
				String filePath = resourceConfig.getFileSpace() + zpath;
				System.out.println(filePath);
				//String[] arrSongPath = zpath.split("\\\\");
				String[] arrSongPath = zpath.split("/");
				String finalPath = "";
				for(int i=0 ; i<arrSongPath.length ; i++) {
					if(StringUtils.isNotBlank(arrSongPath[i])) {
						finalPath+="/";
						finalPath+=URLEncoder.encode(arrSongPath[i], "UTF-8");
					}
				}
				String bgmUrl = resourceConfig.getBgmServer() + finalPath;
				if(zoperType.equals(BGMOperatorTypeEnum.ADD.type)) {				
				URL url = new URL(bgmUrl);
				File file = new File(filePath);
				FileUtils.copyURLToFile(url, file);
				client.delete().forPath(path);
				}else if(zoperType.equals(BGMOperatorTypeEnum.DELETE.type)) {
					File file = new File(filePath);
					FileUtils.forceDelete(file);
					client.delete().forPath(path);
					
				}
			}
				
			}
		});
	}
}
	
	
	
