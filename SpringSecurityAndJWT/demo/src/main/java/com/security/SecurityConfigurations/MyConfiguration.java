package com.security.SecurityConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.JwtConfigurations.CustomJwtAuthenticationFilter;
import com.security.JwtConfigurations.JwtAuthenticationEntryPoint;
@Configuration
@EnableWebSecurity
public class MyConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Autowired
  	private JwtAuthenticationEntryPoint unauthorizedHandler;
	@Bean
	public UserDetailsService getUserDetailService() {
		
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public PasswordEncoder getPassEncoder() {
		
		return  NoOpPasswordEncoder.getInstance();
	}
	@Bean
	public DaoAuthenticationProvider getDaoAuthprovider() {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		dao.setUserDetailsService(getUserDetailService());
		dao.setPasswordEncoder(getPassEncoder());
		return dao;
	}
	@Bean
	public AuthenticationManager getAuthenticationManager()  throws Exception{
		
		return super.authenticationManagerBean();
	}
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(getDaoAuthprovider());
	super.configure(auth);
}
@Override
protected void configure(HttpSecurity http) throws Exception {
     http.authorizeRequests().antMatchers("/getUser").hasAnyRole("ADMIN","USER").antMatchers("/getAdmin").hasRole("ADMIN")
     .antMatchers("/authenticate").permitAll()
 	.and().exceptionHandling()
    .authenticationEntryPoint(unauthorizedHandler).and().
    sessionManagement()
	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.addFilterBefore(customJwtAuthenticationFilter, 
			UsernamePasswordAuthenticationFilter.class)
    ;
	
	
     http.csrf().disable();
	super.configure(http);
}
}
