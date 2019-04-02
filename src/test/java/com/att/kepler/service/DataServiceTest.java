package com.att.kepler.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.att.kepler.service.DataService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataServiceTest {
	public static Logger logger = LoggerFactory.getLogger(DataServiceTest.class);

    @Autowired
    @Qualifier("dataService")
	private DataService service;
    
    @Value("${max_attempt: 5}")
    private int maxAttempt;
	
	@Before
	public void setUp() throws Exception {
		service.insertData("value1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetData() {
		logger.info("Test get data (task 1) ");
		String result = service.getData(maxAttempt);
		logger.info("1. Get data complete , result: "+result);
		assertNotNull(result);
		 
		logger.info("Test get data (task 2) ");
		result = service.getData(maxAttempt);
		logger.info("2. Get data complete , result: "+result);
		assertNull(result);
	}

}
