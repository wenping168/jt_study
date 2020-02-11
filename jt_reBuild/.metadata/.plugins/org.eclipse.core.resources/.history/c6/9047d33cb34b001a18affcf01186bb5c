package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringCloudApplication
@EnableFeignClients 			//开启feign
@EnableCircuitBreaker			//开启hystrix
@EnableHystrixDashboard
public class HystrixRunApp {
	public static void main(String[] args) {
        SpringApplication.run(HystrixRunApp.class, args);
    }
}
