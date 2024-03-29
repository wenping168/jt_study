package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
import com.jt.web.thread.UserThreadLocal;

//璁㈠崟controller
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;
	
	//瀹炵幇璁㈠崟纭椤甸潰鐨勮烦杞�
	@RequestMapping("/create")
	public String toOrderCart(Model model){
		//鏍规嵁鐢ㄦ埛Id鏌ヨ璐墿杞︿俊鎭�
		Long userId = UserThreadLocal.get().getId();
		List<Cart> carts = cartService.findCartListByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	
	//瀹炵幇璁㈠崟鐨勬柊澧�
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order){
		try {
			Long userId = UserThreadLocal.get().getId();
			order.setUserId(userId);
			//鍏ュ簱鍚庤繑鍥炶鍗曞彿
			String orderId = orderService.saveOrder(order);
			//鍒ゆ柇鏁版嵁鏄惁鏈夋晥
			if(StringUtils.isEmpty(orderId)){
				throw new RuntimeException();
			}
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"璁㈠崟鏂板澶辫触");
	}
	
	//瀹炵幇璁㈠崟鐨勬煡璇�
	@RequestMapping("/success")
	public String findOrderById(String id,Model model){
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
}
