package com.springbatch.main;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.springbatch.beans.Employee;

public class ClassReader implements ItemReader<Employee>{

	@Override
	public Employee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Employee employee = new Employee();
		employee.setEmpId("1234");
		employee.setName("GFT");
		System.out.println("Inside ClassReader ..." + employee.toString());
		return employee;
	}

}
