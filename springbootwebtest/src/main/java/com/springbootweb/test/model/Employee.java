package com.springbootweb.test.model;

public class Employee {

	private String nome;
	private Integer id;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Employee(String nome, Integer id) {
		super();
		this.nome = nome;
		this.id = id;
	}
	
}
