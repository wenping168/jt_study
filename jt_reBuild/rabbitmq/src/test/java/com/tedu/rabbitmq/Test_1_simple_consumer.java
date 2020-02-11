package com.tedu.rabbitmq;


import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Test_1_simple_consumer {
	
	@Test
	public void consumer() throws Exception {
		  //1,建立连接
		ConnectionFactory factory=new ConnectionFactory();		
		factory.setHost("192.168.223.138");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		Connection connection=factory.newConnection();
		//2,建立通道
		Channel channel=connection.createChannel();
		//3,定义队列
		//durable true 持久化,重启服务器后，数据还有
	    //exclusive true,只能通过当前连接消费 false
		//autoDelete true 队列中消息处理完后，自动删除队列
		//arguments 参数
		channel.queueDeclare("orderQueue", true, false, false, null);
		
		//4,创建消费者
		QueueingConsumer consumer=new QueueingConsumer(channel);
		//autoAck:自动回复消息
		channel.basicConsume("orderQueue", true, consumer);
		//5,取消息
		while(true){
			Delivery delivery=consumer.nextDelivery();
			byte[] data=delivery.getBody();
			String mString=new String(data);
			System.out.println("消费者取到："+mString);
		}
		      

		

	}

}
