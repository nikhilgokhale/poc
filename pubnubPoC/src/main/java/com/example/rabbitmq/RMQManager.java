package com.example.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RMQManager {

	private static final String TASK_QUEUE_NAME = "messages_queue";
	// publisher would need single connection
	// each thread that needs to publish will create new channel
	private Connection connection = null;
	//consumer thread pool
	private ExecutorService executor = Executors.newFixedThreadPool(5);

	public RMQManager(){
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try {
			connection = factory.newConnection(executor);
			System.out.println("Connected to RabbitMQ server on localhost");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void publish(String message) throws IOException {
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		//send it to default exchange
		channel.basicPublish("", TASK_QUEUE_NAME,
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		channel.close();		
	}
	
	public void close() throws IOException{
		connection.close();
		executor.shutdownNow();
	}
	
	public void subscribe(final MessageHandler handler) throws IOException{
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		//send it to default exchange
	    channel.basicConsume(TASK_QUEUE_NAME, true, new DefaultConsumer(channel){
	    	@Override
	    	public void handleDelivery(String consumerTag, Envelope envelope,
	    			BasicProperties properties, byte[] body) throws IOException {
	    		handler.onMessage(new String(body, "UTF-8"));
	    	}
	    });
	}
	
	public static interface MessageHandler{
		public void onMessage(String message);
	}
}