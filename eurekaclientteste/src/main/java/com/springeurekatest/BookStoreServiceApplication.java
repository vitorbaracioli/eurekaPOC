package com.springeurekatest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.springeurekatest.dto.Customer;
import com.springeurekatest.repository.CustomerRepository;

@RestController
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@RefreshScope
@EnableJpaRepositories("com.springeurekatest.repository")
public class BookStoreServiceApplication {

	@Autowired
	@Lazy
	private CustomerRepository customerRepository;
	
	@Value("${message.recommended}")
	private String messageRecommended;
	
	@Value("${message.buy}")
	private String messageBuy;
	
	@RequestMapping(value = "/recommended")
	public String readingList(){
		return this.messageRecommended;
	}
	
	@RequestMapping(value = "/buy")
	public String buy(){
		return this.messageBuy;
	}

	@RequestMapping(value = "/findCustomerByid/{customerId}")
	public ResponseEntity<Customer> findCustomerByid(@PathVariable(value="customerId") Long id){
		Customer customer = customerRepository.findCustomerById(id);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity
	                .ok()
	                .eTag("\"" + customer.getVersion() + "\"")
	                .body(customer);
		}
	}
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		if (customer != null) {
			customerRepository.save(customer);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<Customer>(customer, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/updateCustomer/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long customerID, @RequestBody Customer customer, @RequestHeader(value="If-Match",required=false,defaultValue="") String ifmatch) {
		
		Customer c = customerRepository.findCustomerById((customerID != null ? customerID : 0L));
				
		if (c == null) {
			return ResponseEntity.notFound().build();
		}
		
		System.out.println("**************************");
		System.out.println("Map Request Header Test");
		System.out.println("IF-MATCH ==> " + ifmatch);
		System.out.println("**************************");
		
		c.setFirstName(customer.getFirstName());
		c.setLastName(customer.getLastName());
		
		customerRepository.save(c);
			
		return ResponseEntity
                .ok()
                .eTag("\"" + c.getVersion() + "\"")
                .body(c);
		
	}
	
//	@Bean
//    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
//        return new ShallowEtagHeaderFilter();
//    }
	
	@Bean
	public CustomerRepository customerRepository() {
		return new CustomerRepository();
	}
	
	@Bean
	public CommandRunnerBookService commandRunnerBookService() {
		return new CommandRunnerBookService();
	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreServiceApplication.class, args);
	}
}
