package com.security.JwtConfigurations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
@Service
public class JwtUtil {
	private String secretKey;
	private int jwtExpirationInMs;
	
	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secretKey = secret;
	}
	
	@Value("${jwt.jwtExpirationInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}
	
	public String generateToken(UserDetails userdetail) {
		Map<String,Object>claims=new HashMap<>();
		Collection<? extends GrantedAuthority>roles=userdetail.getAuthorities();
		if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			claims.put("isAdmin", true);
		}
		if(roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			claims.put("isUser", true);
		}
		
		return doGenerateToken(claims,userdetail.getUsername());
		
		
		
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		
		return Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}
	
	

	public boolean validateToken(String authToken) {
		try {
			// Jwt token has not been tampered with
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		}
		
//		catch (ExpiredJwtException ex) {
//			throw new ex("Token has Expired", ex);
//		}
	}
		
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
	
	public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
		List<SimpleGrantedAuthority> roles = null;
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
		Boolean isAdmin = claims.get("isAdmin", Boolean.class);
		Boolean isUser = claims.get("isUser", Boolean.class);
		if (isAdmin != null && isAdmin == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		if (isUser != null && isUser == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return roles;
	}
	}
	
	
	
	
	

