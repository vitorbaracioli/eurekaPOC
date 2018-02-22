package com.springeurekatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springeurekatest.dto.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Long>{

	Customer findCustomerById(Long id);
	
	@Query("SELECT C FROM Customer C")
	List<Customer> listCustomers();
}
