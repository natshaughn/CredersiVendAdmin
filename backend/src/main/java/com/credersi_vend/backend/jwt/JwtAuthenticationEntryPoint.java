package com.credersi_vend.backend.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {	
		String domain = request.getParameter("domain");
		String customer = request.getParameter("customer");
		String site = request.getParameter("site");
		String machines = request.getParameter("machines");
		
		boolean noDomain = ((domain == null) || (domain.length() < 1));
		boolean noCustomer = ((customer == null) || (customer.length() < 1));
		boolean noSite = ((site == null) || (site.length() < 1));
		boolean noMachines = ((machines == null) || (machines.length() < 1));
		
		if (noDomain && noCustomer && noSite && noMachines) {
			response.sendRedirect("/login");	
		} else {
			String params = "";
			
			if (!noDomain) {
				params = "domain="+domain;
				
				if (!noCustomer) {
					params += "&customer="+customer;
					
					if (!noSite) {
						params += "&site="+site;
						
						if (!noMachines) {
							params += "&machines="+machines;	
						}
					}
				}
			}
			
			response.sendRedirect("/login?"+params);			
		}
	}
}
