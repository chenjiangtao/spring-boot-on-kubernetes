package cn.topstream.app.service;

import cn.topstream.app.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;

import cn.topstream.app.SpringBootK8SApplicationTests;

public class QueueServiceServiceImplTest extends SpringBootK8SApplicationTests {

	@Autowired
	private QueueService queueService;
}
