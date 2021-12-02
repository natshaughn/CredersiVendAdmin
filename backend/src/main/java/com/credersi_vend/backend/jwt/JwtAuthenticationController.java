package com.credersi_vend.backend.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
		
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createToken(@RequestBody JwtRequest request) throws Exception {
		final String username = request.getUsername();
		final String password = request.getPassword();
				
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException ex) {
			System.out.println("Invalid credentials specified on authentication attempt!");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		final UserDetails details = userDetailsService.loadUserByUsername(username);
		
		final String cookie = "Set-Cookie";
		final String prefix = "token=";
		final String suffix = "; Max-Age=604800; HttpOnly";
		final String token = jwtTokenUtil.generate(details);
		
		return ResponseEntity.ok().header(cookie, prefix+token+suffix).body(new JwtResponse(token));
	}
}
