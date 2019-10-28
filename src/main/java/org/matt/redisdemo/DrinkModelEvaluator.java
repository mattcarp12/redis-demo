package org.matt.redisdemo;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class DrinkModelEvaluator implements MessageListener {

	
	public DrinkModelEvaluator() {
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("Message: " + message.toString());
	}

}
