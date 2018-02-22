package com.eurekareadserviceteste.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eurekareadserviceteste.dto.Customer;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@Service
public class BookService {

	
	@Value("${eureka.serviceName}")
	private String bookStoreServiceName;
	
	@Value("${message.fallback.reliable}")
	private String messageFallbackReliable;
	
	@Value("${message.fallback.reliableBuy}")
	private String messageFallbackReliableBuy;
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	@Lazy
	RestTemplate restTemplate;
	
	@Autowired
	@Lazy
	private EurekaClient discoveryClient;
	
	@HystrixCommand(fallbackMethod = "reliable")
	public String readingList(){
		return this.restTemplate.getForObject(URI.create(serviceUrl() + "recommended"), String.class);
	}
	
	public String serviceUrl() {
		InstanceInfo instance = discoveryClient.getNextServerFromEureka(bookStoreServiceName, false);
		return instance.getHomePageUrl();
	}

	public String reliable() {
		return messageFallbackReliable;
	}
	
	@HystrixCommand(fallbackMethod = "reliableBuy")
	public String buy(){
		return this.restTemplate.getForObject(URI.create(serviceUrl() + "buy"), String.class);
	}

	public String reliableBuy() {
		return messageFallbackReliableBuy;
	}
	
	@HystrixCommand(fallbackMethod = "findCustomerByIdFallback")
	public Customer findCustomerById(Long id) throws Exception {
		//ResponseEntity<Customer> result = this.restTemplate.getForEntity(URI.create(serviceUrl() + "findCustomerByid/" + id.toString()), Customer.class);
		return this.restTemplate.getForObject(URI.create(serviceUrl() + "findCustomerByid/" + id.toString()), Customer.class);
	}
	
	public Customer findCustomerByIdFallback(Long id){
		return null;
	
	}
	
}
