package com.springbootweb.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springbootweb.test.model.Employee;

@RestController
@RequestMapping("/api")
public class RestWebController implements IRestWebController {

	@Override
	@RequestMapping(value = "/employees/", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> listAllEmployees() {
		List<Employee> lst = new ArrayList<Employee>();
		lst.add(new Employee("Vitor", 1));
		lst.add(new Employee("Vitor2", 2));
		lst.add(new Employee("Vitor3", 3));
		return new ResponseEntity<List<Employee>>(lst, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@PathVariable(value="employeeId") String id) {

		if (id == null) { return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT); }
		
		if (id.equals("1")) {
			return new ResponseEntity<Employee>(new Employee("Vitor", 1), HttpStatus.OK);
		} else if(id.equals("2")) {
			return new ResponseEntity<Employee>(new Employee("Vitor2", 2), HttpStatus.OK);
		} else if(id.equals("3")) {
			return new ResponseEntity<Employee>(new Employee("Vitor3", 3), HttpStatus.OK);
		} else {
			return new ResponseEntity<Employee>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
	}
	
}
