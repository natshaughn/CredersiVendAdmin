package com.credersi_vend.backend.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.credersi_vend.backend.routes.nodes.DomainRequest;
import com.credersi_vend.routes.CredersiRoute;

@RestController
public class DomainsController extends BaseController {
	
	@Value("${route.domain.environment}")
	private String domainEnvironment;
	
	@Value("${route.domain.name}")
	private String domainName;
	
	@PostMapping(PREFIX+"domains")
	public String createSingleDomain(@RequestBody DomainRequest request) {
		final String name = request.getName();
		final String env = request.getEnvironment();
		
		init();
		UUID uuid = CredersiRoute.CREATE(name, env).Domain().EXECUTE();
		return CredersiRoute.QUERY(uuid).Domain().EXECUTE().toJson();
	}
	
	@DeleteMapping(PREFIX+"domains/{uuid}")
	public String deleteSingleDomain(@PathVariable("uuid") UUID uuid) {
		init();
		
		String json = CredersiRoute.QUERY(uuid).Domain().EXECUTE().toJson();
		CredersiRoute.DELETE(uuid).Domain().EXECUTE();
		
		return json;
	}
	
	@GetMapping(PREFIX+"domains")
	public String queryAllDomains() {
		init();
		
		if ((domainEnvironment != null) && (domainEnvironment.length() > 0) && (domainName != null) && (domainName.length() > 0)) {
			return CredersiRoute.QUERY(domainName, domainEnvironment).Domain().EXECUTE().toJson();
		} else if ((domainName != null) && (domainName.length() > 0)) {
			return CredersiRoute.QUERY(domainName).Domain().EXECUTE().toJson();
		} else {
			return CredersiRoute.QUERY().Domain().EXECUTE().toJson();
		}
	}
	
	@GetMapping(PREFIX+"domains/{uuid}")
	public String querySingleDomain(@PathVariable("uuid") UUID uuid) {
		init();
		return CredersiRoute.QUERY(uuid).Domain().EXECUTE().toJson();
	}
}
