package com.learnk8s.app.service;

import com.learnk8s.app.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;

import com.learnk8s.app.SpringBootK8SApplicationTests;

public class QueueServiceServiceImplTest extends SpringBootK8SApplicationTests {

	@Autowired
	private QueueService queueService;
}
