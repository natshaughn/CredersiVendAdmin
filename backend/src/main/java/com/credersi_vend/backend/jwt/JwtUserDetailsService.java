package com.credersi_vend.backend.jwt;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private static final String NAME = "admin";
	private static final String PASS = "$2a$10$CNwTxcBY7A4DD/ZvDGoV2.WuDuyqds4MCKJgYZVw4yZNIncgayLvC"; // HelloWorld
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!NAME.equals(username)) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		return new User(NAME, PASS, new ArrayList<>());
	}
}
