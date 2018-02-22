package com.springbatch.main;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.springbatch.beans.Employee;

public class ClassWriter implements ItemWriter<Employee>{

	@Override
	public void write(List<? extends Employee> employee) throws Exception {
		System.out.println("Inside ClassWriter ..." + employee.toString());
		
	}

}
