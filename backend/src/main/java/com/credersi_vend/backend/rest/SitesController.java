package com.credersi_vend.backend.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.credersi_vend.backend.routes.nodes.SiteRequest;
import com.credersi_vend.routes.CredersiRoute;

@RestController
public class SitesController extends BaseController {
	
	@PostMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites")
	public String createSingleSite(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@RequestBody SiteRequest request) {
		
		final String address = request.getAddress();
		final String name = request.getName();
		final UUID siteUuid = (request.getUuid() != null) ? request.getUuid() : UUID.randomUUID();
		
		init();
		CredersiRoute.CREATE(domainUuid).Customer(customerUuid).Owns().Site().address(address).name(name).uuid(siteUuid).EXECUTE();
		return CredersiRoute.QUERY(domainUuid).Site(siteUuid).EXECUTE().toJson();
	}
	
	@DeleteMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}")
	public String deleteSingleSite(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid) {
		
		init();
		String json = CredersiRoute.QUERY(domainUuid).Site(siteUuid).EXECUTE().toJson();
		CredersiRoute.DELETE(domainUuid).Site(siteUuid).EXECUTE();
		
		return json;
	}
		
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites")
	public String queryAllSites(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid) {
		
		init();
		return toJsonList(CredersiRoute.LIST(domainUuid).Customer(customerUuid).Owns().Site().EXECUTE());
	}
	
	@GetMapping(PREFIX+"domains/{domainUuid}/customers/{customerUuid}/sites/{siteUuid}")
	public String querySingleSite(
			@PathVariable("domainUuid") UUID domainUuid,
			@PathVariable("customerUuid") UUID customerUuid,
			@PathVariable("siteUuid") UUID siteUuid) {
		
		init();
		return CredersiRoute.QUERY(domainUuid).Site(siteUuid).EXECUTE().toJson();
	}
}
