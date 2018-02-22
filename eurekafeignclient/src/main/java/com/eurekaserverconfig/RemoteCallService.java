package com.eurekaserverconfig;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eurekaserverconfig.dto.Customer;

@FeignClient(name="bookStoreService")

public interface RemoteCallService {
	
	@RequestMapping(method=RequestMethod.GET, value="/findCustomerByid/{customerId}")
	ResponseEntity<Customer> findCustomerByid(@PathVariable(value="customerId") Long id);

	@RequestMapping(method = RequestMethod.POST, value = "/addCustomer", consumes = "application/json")
	ResponseEntity<Customer> addCustomer(@RequestBody Customer customer);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/updateCustomer/{customerId}", consumes = "application/json")
	ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody Customer customer, @RequestHeader(value="If-Match",required=false,defaultValue="") String ifmatch);
}
