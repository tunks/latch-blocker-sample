package com.att.kepler.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.stereotype.Service;

import com.att.kepler.support.DataUtil;

@Service("dataService")
public class DataService implements InitializingBean{
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String,String> redisOperations;
	
	@Value("${queue.name: data-queue}")
	private String queueName;
	
	private BlockingQueue<String> queue = new  LinkedBlockingQueue<String>();
	
	public String getData(int max) {
		 return DataUtil.runTask(max, () -> {
		    String result = queue.poll();
		    return DataUtil.handleResult(result);
		});		
	}

	
	public void insertData(String data) {
		queue.add(data);
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Data service started");
		queue = new DefaultRedisList<String>(queueName,redisOperations);
		
	}
}
