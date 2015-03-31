package com.example.pubnub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import com.example.pubnub.impl.PubSubServiceImpl;
import com.example.stats.FileLogger;
import com.pubnub.api.Callback;

public class PubnubDemo {
	public static void main(String[] args) {
		PubSubService pubsubService = new PubSubServiceImpl();
		System.out.println("Enter comma separated channel names to subscribe:");
		String channel = readInput();
		String logFileName = System.getProperty("user.home")+"/pubnub_destination.csv";
		System.out.println("Logging stats to "+ logFileName);
		final FileLogger logger = new FileLogger(logFileName);
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
		pubsubService.subscribe(StringUtils.split(channel, ','), callback);

		while(true){
			System.out.println("Enter comma separated channel names to publish:");
			channel = readInput();
		
			System.out.println("Enter message:");			
			String msg = readInput();
		
			pubsubService.publish(StringUtils.split(channel, ','), msg);
		}
	}
	private static String readInput() {
		String channel;
		try {
			channel = new BufferedReader(new InputStreamReader(
			        System.in)).readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return channel;
	}
}
