package com.nicchagil.springrabbitexercise;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class UserSyncQueueConsumerManualAck implements ChannelAwareMessageListener {
	
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		boolean multiple = false;
		boolean requeue = false;
		
		if (new Random().nextInt(2) == 0) {
			// 模拟宕机
			// TimeUnit.MINUTES.sleep(5);
			
			// 模拟异常
			throw new RuntimeException();
		}
		
		try {
			MessageProperties messageProperties = message.getMessageProperties();
			byte[] bytes = message.getBody();
			
			logger.info("messageProperties : " + messageProperties);
			logger.info("body : " + new String(bytes));
			
			if (new Random().nextInt(2) == 0) {
				// 模拟宕机
				// TimeUnit.MINUTES.sleep(5);
				
				// 模拟异常
				// throw new RuntimeException();
			}
			
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), multiple); // 确认此消息，并只确认此消息（false为只确认此消息）
			logger.info("Acked");
		} catch (Exception e) {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), multiple, requeue); // 确认此消息处理异常
			logger.info("Nacked");
		}
		
	}

}
