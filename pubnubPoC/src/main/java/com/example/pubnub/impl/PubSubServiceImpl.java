package com.example.pubnub.impl;

import com.example.pubnub.PubSubService;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

public class PubSubServiceImpl implements PubSubService{
	@Override
	public void publish(String[] channelNames, String message) {
		Callback callback = new Callback() {
			@Override
			public void errorCallback(String channel, Object error) {
				System.err.println("Error while publishing to channel "+ channel+". Message = "+error.toString());				
			}
			@Override
			public void disconnectCallback(String channel, Object error) {
				System.err.println("Disconncected from "+channel +":"+error.toString());
			}
			@Override
			public void successCallback(String channel, Object message) {
			}
		};
		publish(channelNames, message, callback);
	}

	@Override
	public void subscribe(String[] channelNames, Callback callback) {
		Pubnub pubnub = null;
		try {
			pubnub = PubnubPool.POOL_INSTANCE.takeFromPool();
			pubnub.subscribe(channelNames, callback);
		} catch (PubnubException e) {
			throw new RuntimeException(e);
		}finally{
			if(pubnub != null){
				PubnubPool.POOL_INSTANCE.returnToPool(pubnub);
			}
		}
	}

	@Override
	public void publish(String[] channelNames, String message, Callback callback) {
		Pubnub pubnub = null;
		try{
			pubnub = PubnubPool.POOL_INSTANCE.takeFromPool();
			for (String channel : channelNames) {
				pubnub.publish(channel, message, callback);
			}
		}finally{
			if(pubnub != null){
				PubnubPool.POOL_INSTANCE.returnToPool(pubnub);
			}
		}
	}

}
