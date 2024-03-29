package org.matt.redisdemo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class RedisDemoApplication {

	@Value("${spring.redis.url}")
	URI redisURI;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(RedisDemoApplication.class, args);

		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		template.convertAndSend("channel2", "Hello from Redis!");

	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter messageListener) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(messageListener, new ChannelTopic("channel1"));

		return container;
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(getReceiver(), "receiveMessage");
	}

	@Bean
	Receiver getReceiver() {
		return new Receiver();
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(redisURI.getHost());
		config.setPort(redisURI.getPort());
		if (!redisURI.getHost().equals("localhost"))
			config.setPassword(redisURI.getUserInfo().substring(2));
		return new JedisConnectionFactory(config);
	}

}
