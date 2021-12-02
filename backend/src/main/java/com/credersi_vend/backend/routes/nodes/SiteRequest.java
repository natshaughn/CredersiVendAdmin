package com.credersi_vend.backend.routes.nodes;

import java.io.Serializable;
import java.util.UUID;

public class SiteRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String address;
	private String name;
	private UUID uuid;
	
	public SiteRequest() {
		// Default constructor required for JSON Parsing	
	}
	
	public SiteRequest(String address, String name) {
		this.setAddress(address);
		this.setName(name);
	}
	
	public SiteRequest(String address, String name, UUID uuid) {
		this.setAddress(address);
		this.setName(name);
		this.setUuid(uuid);
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getName() {
		return this.name;
	}
	
	public UUID getUuid() {
		return this.uuid;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
