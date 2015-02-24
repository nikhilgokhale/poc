package com.example.pubnub.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.pubnub.api.Pubnub;

public class PubnubPool {
	private static final int POOL_MAX_SIZE = 5;

	private BlockingQueue<Pubnub> PUBNUB_POOL = new ArrayBlockingQueue<Pubnub>(POOL_MAX_SIZE);
	public static PubnubPool POOL_INSTANCE = new PubnubPool();
	private PubnubPool(){
		for(int i=0; i <POOL_MAX_SIZE; i++){
			try {
				PUBNUB_POOL.put(new Pubnub("pub-c-8a9b8d17-f888-40f4-98c7-e7a437a95bcd", "sub-c-cdfc92a8-a965-11e4-aa71-02ee2ddab7fe"));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public Pubnub takeFromPool(){
		try {
			return PUBNUB_POOL.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	public void returnToPool(Pubnub pubnub){
		try {
			PUBNUB_POOL.put(pubnub);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
