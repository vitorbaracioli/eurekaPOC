package com.springbootweb.test.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.springbootweb.test.model.Employee;

public interface IRestWebController {

	ResponseEntity<List<Employee>> listAllEmployees();
	
	ResponseEntity<Employee> getEmployee(String id);
}
