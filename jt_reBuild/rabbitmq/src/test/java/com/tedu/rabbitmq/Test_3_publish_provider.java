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
public class Test_3_publish_provider {
	
	@Test
	public void provider() throws Exception {
		 //1,建立连接
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("192.168.223.138");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		//2,创建通道
		//com.rabbitmq.client.Connection
		Connection connection=factory.newConnection();
		//com.rabbitmq.client
		Channel channel=connection.createChannel();
		 // 定义交换机名称
		String exchange_name = "el";
		/**
		 * 创建交换机
		 * p2：type
		 * fanout订阅模式
		 * direct 路由模式
		 * topic 主题模式
		 */
		// fanout是定义发布订阅模式  direct是 路由模式 topic是主题模式
		channel.exchangeDeclare(exchange_name, "fanout");

		String msg = "order1" ;
		channel.basicPublish(exchange_name, "", null, msg.getBytes());

		channel.close();
		connection.close();
		//订阅，路由，主题数据发成功之后，后台看不到数量
		       


	}

}
