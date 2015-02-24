package com.example.pubnub;

import com.example.pubnub.impl.PubSubServiceImpl;
import com.example.stats.FileLogger;
import com.pubnub.api.Callback;

public class PubnubDemoSubscriber {
	public static void main(String[] args) {
		PubSubService pubsubService = new PubSubServiceImpl();
		final FileLogger logger = new FileLogger("e:/pubnub_destination.csv");
		Callback callback = new Callback() {
			@Override
			public void connectCallback(String channel, Object msg) {
				System.out.println("Connected to channel: " + channel+":"+msg.toString());
			}
			@Override
			public void disconnectCallback(String channel, Object msg) {					
				System.out.println("Connected to channel: " + channel+":"+msg.toString());
			}
			@Override
			public void reconnectCallback(String channel, Object msg) {
				System.out.println("Connected to channel: " + channel+":"+msg.toString());
			}
			@Override
			public void successCallback(String channel, Object msg) {
				logger.log(msg.toString());
			}
			
		};
		pubsubService.subscribe(new String[]{"demo"}, callback);
		System.out.println("Subscribed to demo channel");
	}

}
