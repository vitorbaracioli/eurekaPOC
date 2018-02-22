package com.eurekaserverconfig;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.eurekaserverconfig.dto.Customer;

@SpringBootApplication
@EnableFeignClients
public class EurekafeignclientApplication {
	
//	@Bean
//	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
//	   return new ShallowEtagHeaderFilter();
//	}

	public static void main(String[] args) throws RestClientException, IOException {
		ApplicationContext ctx = SpringApplication.run(EurekafeignclientApplication.class, args);
		
		CustomerControllerClient controller = ctx.getBean(CustomerControllerClient.class);
		ResponseEntity<Customer> customer = controller.findCustomerById(1L);
		
		System.out.println(customer.toString());
		System.out.println("ETAG ==> " + customer.getHeaders().getETag());
		
		//System.out.println("IF-MATCH ==> " + customer.getHeaders().get("if-match").toString());
		
		//Customer c = new Customer("Vitor", "Baracioli");
		//controller.addCustomer(c);
		
		Customer c2 = new Customer(1L, "Jack", "Bauer Baracioli");
		ResponseEntity<Customer> c_ = controller.updateCustomer(1L, c2);
		System.out.println("ETAG Updated ==> " + c_.getHeaders().getETag());
		System.out.println("IF-MATCH ==> " + c_.getHeaders().get("if-match").toString());
		
	}
}
