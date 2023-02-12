package com.security.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.Entity.AuthenticationRequest;
import com.security.Entity.AuthenticationResponse;
import com.security.JwtConfigurations.JwtUtil;
import com.security.SecurityConfigurations.UserDetailServiceImpl;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
@PostMapping("/authenticate")
public ResponseEntity<AuthenticationResponse> createAthenticationToken(@RequestBody AuthenticationRequest  authenticationRequest) throws Exception{
	try{authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),authenticationRequest.getPassword()));
	System.out.println("HI bardoe");
	
	}
	catch(DisabledException e) {
		throw new Exception("user_disabled",e);
		
	}catch(BadCredentialsException e) {
		throw new Exception("Invalid_crediantials",e);
		
	}
	final UserDetails userDetails=userDetailServiceImpl.loadUserByUsername(authenticationRequest.getUserName());
	final String token=jwtUtil.generateToken(userDetails);
	
	return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(token),HttpStatus.CREATED);
}	
	

}
