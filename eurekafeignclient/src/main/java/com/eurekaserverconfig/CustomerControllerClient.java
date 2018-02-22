package com.eurekaserverconfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;

import com.eurekaserverconfig.dto.Customer;

@Controller
public class CustomerControllerClient {

	@Autowired
	private RemoteCallService loadBalancer;

	public ResponseEntity<Customer> findCustomerById(Long id) throws RestClientException, IOException {
		ResponseEntity<Customer> result = null;
		
		try {
			 result = loadBalancer.findCustomerByid(id);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public ResponseEntity<Customer> addCustomer(Customer customer) throws RestClientException, IOException {
		ResponseEntity<Customer> result = null;
		
		try {
			 result = loadBalancer.addCustomer(customer);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public ResponseEntity<Customer> updateCustomer(Long customerId, Customer customer) throws RestClientException, IOException {
		ResponseEntity<Customer> result = null;
		
		try {
			 result = loadBalancer.updateCustomer(customerId, customer, null);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
}
