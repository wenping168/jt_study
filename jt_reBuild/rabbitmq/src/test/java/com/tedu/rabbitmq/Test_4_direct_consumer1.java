package com.tedu.rabbitmq;


import java.util.UUID;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
/**
 * 工作模式消费者1
 * @author WP
 *
 */
public class Test_4_direct_consumer1 {
	
	@Test
	public void consumer() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.223.138");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		Connection	connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		String exchange_name = "E2";
		//定义交换机模式
		channel.exchangeDeclare(exchange_name, "direct");
		
		String queue_name = UUID.randomUUID().toString();
		System.out.println("队列名称"+queue_name);
		//定义队列
		channel.queueDeclare(queue_name, true, false, false, null);
		//将队列和交换机绑定   key:表示接收数据标识
		channel.queueBind(queue_name, exchange_name, "agent");
		//定义消费数量 
		channel.basicQos(1);
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//将消费者和队列绑定,并且需要手动返回
		channel.basicConsume(queue_name, false, consumer);
		System.out.println("消费者2代购库");

		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println(msg+"入代购库");
			//false表示一个一个返回
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}



	}

}
