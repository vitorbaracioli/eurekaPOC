package com.eurekareadserviceteste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.eurekareadserviceteste.dto.Customer;
import com.eurekareadserviceteste.service.BookService;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
@EnableHystrixDashboard
@EnableEurekaClient
@RefreshScope
public class ReadingApplication {
	
	@Autowired
	private BookService bookService;

	@RequestMapping("/to-read")
	public String toRead() {
		return bookService.readingList();
	}
	
	@RequestMapping("/to-buy")
	public String toBuy() {
		return bookService.buy();
	}
	
	@RequestMapping("/findCustomerById/{customerId}")
	public ResponseEntity<Customer> findCustomerById(@PathVariable("customerId") Long Id) throws Exception {
		Customer customer = bookService.findCustomerById(Id);
		if (customer == null) {
			return new ResponseEntity<Customer>(new Customer("FALLBACK","FALLBACK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		}
	}
	
//	@Bean
//    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
//        return new ShallowEtagHeaderFilter();
//    }

	public static void main(String[] args) {
		SpringApplication.run(ReadingApplication.class, args);
	}
}
