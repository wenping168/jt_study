package cn.tedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/hello/{name}")
	@ResponseBody
	public String hello(@PathVariable String name){
		String url = "http://provider-user/hello/"+name;		//VIP虚拟IP，提供者的application-name:provider-user
		return this.restTemplate.getForObject(url, String.class);
	}
	
	@GetMapping("/sidecar")
	public String home(){
		String url = "http://sidecar/";		//通过Zuul调用Nodejs的服务
		return this.restTemplate.getForObject(url, String.class);
	}
}
