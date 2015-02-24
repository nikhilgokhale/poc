package com.example.rabbitmq;

import com.example.pubnub.PubSubService;
import com.example.pubnub.impl.PubSubServiceImpl;
import com.example.rabbitmq.RMQManager.MessageHandler;

public class RMQSubscriber {
	public static void main(String[] args) throws Exception{
		RMQManager manager = new RMQManager();
		final PubSubService pubnubService = new PubSubServiceImpl();
		manager.subscribe(new MessageHandler() {			
			@Override
			public void onMessage(String message) {
				pubnubService.publish(new String[]{"demo"}, message);
			}
		});
	}
}
