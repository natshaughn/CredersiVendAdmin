package com.credersi_vend.backend.routes.nodes;

import java.io.Serializable;
import java.util.UUID;

public class MachineRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String directions;
	private String location;
	private String name;
	private UUID uuid;
	
	public MachineRequest() {
		// Default constructor required for JSON Parsing	
	}
	
	public MachineRequest(String location, String name) {
		this.setLocation(location);
		this.setName(name);
	}
	
	public MachineRequest(String directions, String location, String name) {
		this.setDirections(directions);
		this.setLocation(location);
		this.setName(name);
	}
	
	public MachineRequest(String directions, String location, String name, UUID uuid) {
		this.setDirections(directions);
		this.setLocation(location);
		this.setName(name);
		this.setUuid(uuid);
	}
	
	public String getDirections() {
		return this.directions;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getName() {
		return this.name;
	}
	
	public UUID getUuid() {
		return this.uuid;
	}
	
	public void setDirections(String directions) {
		this.directions = directions;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
