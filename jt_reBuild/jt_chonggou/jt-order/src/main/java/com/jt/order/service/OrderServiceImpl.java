package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

//@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	//操作rabbitmq服务器对象
	@Autowired
	private RabbitTemplate rabbitTemplate;

	
	@Override
	public String saveOrder(Order order) {
		//鎷兼帴orderId鍙�
		String orderId = order.getUserId() + "" + System.currentTimeMillis();
		order.setOrderId(orderId);
		//使用消息队列将数据存入rabbitmq中
		String routingKey = "saveorder";
		//将order对象序列化，变成字符串，发送到rabbitmq服务器上
		rabbitTemplate.convertAndSend(routingKey , order);
		
		Date date = new Date();
		//鍏ュ簱璁㈠崟
		order.setOrderId(orderId);
		order.setStatus(1); 
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("璁㈠崟鍏ュ簱鎴愬姛!!!!!");
		
		//鑾峰彇璁㈠崟鐗╂祦淇℃伅
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("璁㈠崟鐗╂祦淇℃伅鍏ュ簱鎴愬姛!!");
		
		//瀹炵幇璁㈠崟鍟嗗搧鍏ュ簱
		List<OrderItem> orderItemList = order.getOrderItems();
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("璁㈠崟鍏ュ簱鎴愬姛!!!");
		
		return orderId;
	}
	
	//涓夊紶琛ㄥ悓鏃舵煡璇�
	/**
	 *鎬濊矾:
	 *	1.where 2.left join  tb_order tb_order_shipping tb_order_item
	 *	Mybatis瑙勫畾:缁撴灉闆嗕腑绂佹鍑虹幇閲嶅悕瀛楁. mybaits杩涜缁撴灉闆嗘槧灏勬椂蹇呭畾鎶ラ敊.
	 *	select a.order_id as order_id,b.order_id as b_order_id from  tb_order a,tb_order_shipping b,tb_order_item c
	 *	where a.order_id = #{orderId} and a.order_id = b.order_id and b.order_id = c.order_id
	 *	
	 *	2.宸﹁繛鎺ユ煡璇�
	 *		select *  from 
(select o.order_id............... from
tb_order o
	LEFT JOIN
tb_order_item b 
	on 
	o.order_id = b.order_id
where o.order_id = '71521427672071')c
	LEFT JOIN
tb_order_shipping d
	on c.order_id = d.order_id
	 *
	 *  resultMap瀹炵幇鏁版嵁搴撴槧灏�.
	 */
	@Override
	public Order findOrderById(String orderId) {
		//1.鑾峰彇order鏁版嵁  2.鑾峰彇orderShipping瀵硅薄 3.鑾峰彇orderItem鏁版嵁
		//4.灏嗘暟鎹繘琛岀粍瑁�
		Order order = orderMapper.selectByPrimaryKey(orderId);
		OrderShipping orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
		
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);
		List<OrderItem> orderItems = orderItemMapper.select(orderItem);
		
		//鏁版嵁灏佽
		order.setOrderShipping(orderShipping);
		order.setOrderItems(orderItems);
		return order;
	}
}
