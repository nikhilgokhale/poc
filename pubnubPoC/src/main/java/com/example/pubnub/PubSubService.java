package com.example.pubnub;

import com.pubnub.api.Callback;

public interface PubSubService {
	void publish(String[] channelName, String message);
	void publish(String[] channelName, String message, Callback callback);
	void subscribe(String[] channelNames, Callback callback);
}
