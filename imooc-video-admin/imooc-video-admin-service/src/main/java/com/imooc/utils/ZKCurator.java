package com.imooc.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKCurator {
	private CuratorFramework client;

	final static Logger log = LoggerFactory.getLogger(ZKCurator.class);
	
	public ZKCurator(CuratorFramework client) {
		super();
		this.client = client;
	}
	public void init() {
		client = client.usingNamespace("admin");
		try {
			if(client.checkExists().forPath("/bgm") == null) {
				client.create().creatingParentsIfNeeded()
				   .withMode(CreateMode.PERSISTENT)
				   .withACL(Ids.OPEN_ACL_UNSAFE)
				   .forPath("/bgm");
				log.info("初始化成功");
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("zookeeper客户端，链接初始化错误");
			e.printStackTrace();
		}
	}
	
	public void sendBgmOperation(String bgmId , String operType) {
		System.out.println(bgmId);
		try {
			client.create().creatingParentsIfNeeded()
			  .withMode(CreateMode.PERSISTENT)
			  .withACL(Ids.OPEN_ACL_UNSAFE)
			  .forPath("/bgm/" + bgmId,operType.getBytes());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
