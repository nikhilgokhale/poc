package com.test.pubnub.web;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.pubnub.PubSubService;
import com.example.pubnub.impl.PubSubServiceImpl;
import com.example.rabbitmq.RMQManager;
import com.example.stats.FileLogger;

@Controller
public class PubController {
	private static final FileLogger LOGGER = new FileLogger("e:/pubnub_source.csv");
	private PubSubService pubsubService = new PubSubServiceImpl();
	private RMQManager rmqManager = new RMQManager();
	
	@RequestMapping(value="/direct", method=RequestMethod.GET)
	@ResponseBody
	public String directPublish() throws Exception{
		String uuid = UUID.randomUUID().toString();
		LOGGER.log(uuid);
		pubsubService.publish(new String[] {"demo"}, uuid);
		return "0";
	}
	
	@RequestMapping(value="/routed", method=RequestMethod.GET)
	@ResponseBody	
	public String routedPublish() throws Exception{
		String uuid = UUID.randomUUID().toString();
		LOGGER.log(uuid);
		rmqManager.publish(uuid);
		return "0";		
	}
}
