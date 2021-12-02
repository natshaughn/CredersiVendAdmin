package com.credersi_vend.backend.routes.nodes;

import java.io.Serializable;
import java.util.UUID;

public class CustomerRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private UUID uuid;
	
	public CustomerRequest() {
		// Default constructor required for JSON Parsing	
	}
	
	public CustomerRequest(String name) {
		this.setName(name);
	}
	
	public CustomerRequest(String name, UUID uuid) {
		this.setName(name);
		this.setUuid(uuid);
	}
		
	public String getName() {
		return this.name;
	}
	
	public UUID getUuid() {
		return this.uuid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
