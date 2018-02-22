package com.springbatchteste;

import org.springframework.batch.item.ItemProcessor;


public class ClassProcessor implements ItemProcessor<Employee, Employee>{

	@Override
	public Employee process(Employee employee) throws Exception {
		employee.setName(employee.getName() + " 1");
		System.out.println("Inside ClassProcessor ... " + employee.toString());
		return employee;
	}

	
	
}
