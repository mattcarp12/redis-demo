package org.matt.redisdemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class Receiver implements MessageListener {
	
	Logger logger = LoggerFactory.getLogger(Receiver.class);
	
	public Receiver() {	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		logger.info(message.toString());
	}
}