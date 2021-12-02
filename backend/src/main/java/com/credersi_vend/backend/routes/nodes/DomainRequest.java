package com.credersi_vend.backend.routes.nodes;

import java.io.Serializable;

public class DomainRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String environment;
	private String name;
	
	public DomainRequest() {
		// Default constructor required for JSON Parsing	
	}

	public DomainRequest(String environment, String name) {
		this.setEnvironment(environment);
		this.setName(name);
	}
	
	public String getEnvironment() {
		return this.environment;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
