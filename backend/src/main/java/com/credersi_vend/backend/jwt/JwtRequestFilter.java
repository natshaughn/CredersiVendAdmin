package com.credersi_vend.backend.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private String getUsername(String token) {
		try {
			return jwtTokenUtil.getUsername(token);
		} catch (IllegalArgumentException e) {
			System.out.println("Unable to get JWT Token");
			return null;
		} catch (ExpiredJwtException e) {
			System.out.println("JWT Token has expired");
			return null;
		}
	}
	
	private String extractFromAuth(HttpServletRequest request) {
		final String bearer = request.getHeader("Authorization");
		final String prefix = "Bearer ";
		
		if ((bearer == null) || (!bearer.startsWith(prefix))) {
			return null;
		}
		
		return bearer.substring(prefix.length());
	}
	
	private String extractFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("token")) {
				return cookie.getValue();
			}
		}
		
		return null;
	}
	
	private String extractToken(HttpServletRequest request) {
		String token = extractFromAuth(request);
		if (token != null) {
			return token;
		}
		
		token = extractFromCookie(request);
		if (token != null) {
			return token;
		}
		
		logger.warn("No authentiation specified as authorization in the request!");
		return null;
	}
		
	private void processBearer(HttpServletRequest request) {
		//
		// Nothing to process if the authentication request
		//
		
		if (request.getServletPath().equalsIgnoreCase("/authenticate")) {
			logger.info("Bearer not required as endpoint is for credentials check!");
			return;
		}
				
		//
		// Nothing to process if already authenticated
		//
		
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			logger.info("Already authenticated!");
			return;
		}
		
		//
		// Extract authorisation token
		//
		
		String token = extractToken(request);
		if (token == null) {
			return;
		}
		
		//
		// Extract user name from the JWT
		//
				
		String username = getUsername(token);
		if (username == null) {
			logger.warn("Failed to extract username from the JWT!");
			return;
		}
		
		//
		// Validate JWT
		//
		
		UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
		if (!jwtTokenUtil.validate(token, userDetails)) {
			logger.warn("Failed to validate JWT for authentication!");
			return;
		}
		
		//
		// Finally, now validated, manually set Spring Security authentication
		//
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		processBearer(request);
		chain.doFilter(request, response);
	}
}
