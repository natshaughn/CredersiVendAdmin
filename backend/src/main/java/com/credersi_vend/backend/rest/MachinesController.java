package com.credersi_vend.backend.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.credersi_vend.backend.routes.nodes.MachineRequest;
import com.credersi_vend.routes.CredersiRoute;

@RestController
public class MachinesController extends BaseController {
	
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/origin")
	public String querySingleMachine(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid) {
		
		init();
		return CredersiRoute.QUERY(domainUuid).Site(siteUuid).origin().EXECUTE().toJson();
	}
	
	@PostMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/origin")
	public String createSingleMachine(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid,
			@RequestBody MachineRequest request) {
		
		final String directions = request.getDirections();
		final String location = request.getLocation();
		final String name = request.getName();
		final UUID machineUuid = (request.getUuid() != null) ? request.getUuid() : UUID.randomUUID();
		
		init();
		CredersiRoute.CREATE(domainUuid).Site(siteUuid).Route().directions(directions).Machine().location(location).name(name).uuid(machineUuid).EXECUTE();
		return CredersiRoute.QUERY(domainUuid).Machine(machineUuid).EXECUTE().toJson();
	}
	
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/machines/{machineUuid}")
	public String querySingleMachine(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid,
			@PathVariable("machineUuid") UUID machineUuid) {
		
		init();
		return CredersiRoute.QUERY(domainUuid).Machine(machineUuid).EXECUTE().toJson();
	}
	
	@PostMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/route/{machineUuid}")
	public String routeAppendMachine(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid,
			@PathVariable("machineUuid") UUID routeUuid,
			@RequestBody MachineRequest request) {
		
		final String directions = request.getDirections();
		final String location = request.getLocation();
		final String name = request.getName();
		final UUID machineUuid = (request.getUuid() != null) ? request.getUuid() : UUID.randomUUID();
		
		init();
		CredersiRoute.CREATE(domainUuid).Machine(routeUuid).Route().directions(directions).Machine().location(location).name(name).uuid(machineUuid).EXECUTE();
		return CredersiRoute.QUERY(domainUuid).Machine(machineUuid).EXECUTE().toJson();
	}
		
	@DeleteMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/route/{machineUuid}")
	public String routeDeleteRecursive(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid,
			@PathVariable("machineUuid") UUID routeUuid) {
		
		init();
		String json = toJsonPairs(CredersiRoute.LIST(domainUuid).Machine(routeUuid).Route().Machine().EXECUTE());
		CredersiRoute.DELETE(domainUuid).Machine(routeUuid).EXECUTE();
		
		return json;
	}
	
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/route")
	public String routeListComplete(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid) {
		
		init();
		return toJsonPairs(CredersiRoute.LIST(domainUuid).Site(siteUuid).Route().Machine().EXECUTE());
	}
	
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}/route/{machineUuid}")
	public String routeListPartial(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid,
			@PathVariable("machineUuid") UUID routeUuid) {
		
		init();
		return toJsonPairs(CredersiRoute.LIST(domainUuid).Machine(routeUuid).Route().Machine().EXECUTE());
	}
}
