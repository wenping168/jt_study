package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import cn.config.RibbonRuleConfig;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="provider-user", configuration=RibbonRuleConfig.class)	//只配置provider-user为随机访问
public class ConsumerRunApp {
	@Bean
	@LoadBalanced	//Ribbon负载均衡
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConsumerRunApp.class, args);
	}
}
