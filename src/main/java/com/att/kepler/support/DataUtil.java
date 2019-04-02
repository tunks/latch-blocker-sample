package com.att.kepler.support;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataUtil {
	public static Logger logger = LoggerFactory.getLogger(DataUtil.class);

	public static <T extends Serializable> T runTask(int maxAttempt, Callable<T> task) {
		logger.info("Task starts");
		CountDownLatch latch = new CountDownLatch(maxAttempt);
		T result = null;
		while (latch.getCount() > 0) {
			try {
				result = task.call();
				break; //break is optional or direct return 
			} catch (Exception e) {
				logger.warn(e.getMessage());
				try {
					latch.await(1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e1) {
					logger.error(e1.getMessage());
				}
				finally {
					latch.countDown();
				}
			}
		}
		logger.info("Task ends");
		return result;
	}
	
	
	public static <T extends Serializable> T handleResult(T result) throws NoDataException{
		if(result != null)
		   return result;
	    throw new NoDataException("No data found");
	}
	
}
