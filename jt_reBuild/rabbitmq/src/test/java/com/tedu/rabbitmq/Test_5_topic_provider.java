package com.tedu.rabbitmq;


import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * 工作模式消息提供者
 * @author WP
 *
 */
public class Test_5_topic_provider {
	
	@Test
	public void provider() throws Exception {
		System.out.println("开始发布消息");
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.223.138");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		Connection connection = factory.newConnection();
		// 定义通道
		Channel channel = connection.createChannel();
		// 定义交换机名称
		String exchange_name = "E3";
		// fanout是定义发布订阅模式  direct是 路由模式 topic是主题模式
		channel.exchangeDeclare(exchange_name, "topic");

		String msg = "msg10" ;
	    channel.basicPublish(exchange_name, "domestic.cart", null, msg.getBytes());
		channel.basicPublish(exchange_name, "domestic.order", null, msg.getBytes());
		channel.basicPublish(exchange_name, "agent.cart", null, msg.getBytes());
		channel.basicPublish(exchange_name, "agent.order", null, msg.getBytes());

		channel.close();
		connection.close();

	}

}
