package com.credersi_vend.backend.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Value("${jwt.expirationInMilliseconds}")
	private long expiration;
	
	@Value("${jwt.secretEncodedAsBase64}")
	private String secret;
	
	private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}
	
	public String generate(UserDetails userDetails) {		
		return Jwts.builder()
			.setClaims(new HashMap<>())
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setSubject(userDetails.getUsername())
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
	}
	
	public Date getExpirationDate(String token) {
		return getClaim(token, Claims::getExpiration);
	}
	
	public String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}
	
	public Boolean isExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date());
	}
	
	public Boolean validate(String token, UserDetails userDetails) {
		final String username = getUsername(token);
		return (username.equals(userDetails.getUsername()) && (!isExpired(token)));
	}
}
