package com.credersi_vend.backend.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.credersi_vend.backend.routes.nodes.CustomerRequest;
import com.credersi_vend.routes.CredersiRoute;

@RestController
public class CustomersController extends BaseController {
	
	@PostMapping(PREFIX+"domains/{domainUuid}/customers")
	public String createSingleCustomer(
			@PathVariable("domainUuid") UUID domainUuid,
			@RequestBody CustomerRequest request) {
		
		final String name = request.getName();
		final UUID customerUuid = (request.getUuid() != null) ? request.getUuid() : UUID.randomUUID();
		
		init();
		CredersiRoute.CREATE(domainUuid).Domain().Supplies().Customer(customerUuid).name(name).EXECUTE();
		return CredersiRoute.QUERY(domainUuid).Customer(customerUuid).EXECUTE().toJson();
	}
	
	@DeleteMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}")
	public String deleteSingleCustomer(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid) {
		
		init();
		String json = CredersiRoute.QUERY(domainUuid).Customer(customerUuid).EXECUTE().toJson();
		CredersiRoute.DELETE(domainUuid).Customer(customerUuid).EXECUTE();
		
		return json;
	}
		
	@GetMapping(PREFIX+"domains/{uuid}/customers")
	public String queryAllCustomers(@PathVariable("uuid") UUID uuid) {
		init();
		return toJsonList(CredersiRoute.LIST(uuid).Domain().Supplies().Customer().EXECUTE());
	}
	
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}")
	public String querySingleCustomer(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid) {
		
		init();
		return CredersiRoute.QUERY(domainUuid).Customer(customerUuid).EXECUTE().toJson();
	}
}
