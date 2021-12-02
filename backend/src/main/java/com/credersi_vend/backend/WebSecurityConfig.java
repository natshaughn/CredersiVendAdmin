package com.credersi_vend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.credersi_vend.backend.jwt.JwtAuthenticationEntryPoint;
import com.credersi_vend.backend.jwt.JwtRequestFilter;
import com.credersi_vend.backend.jwt.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		final PasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(encoder);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// Beware, as copied code disables Cross-Site Request Forgery Prevention
			.csrf().disable()
			
			// The permit end-points that need no bearer header check
			.authorizeRequests()
				.antMatchers("/authenticate").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/global.css").permitAll()
				.antMatchers("/images/CredersiLogo.png").permitAll()
				.antMatchers("/images/favicon.png").permitAll()
			
			// All others need to be authenticated with a header in the request
			.anyRequest().authenticated().and()
	        
			// Ensure using state-less sessions as JWT provided with each request
			.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
