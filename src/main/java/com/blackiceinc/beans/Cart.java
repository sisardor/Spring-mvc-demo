package com.blackiceinc.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Cart {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cart(String name) {
		super();
		this.name = name;
	}

	public Cart() {
		super();
	}

}