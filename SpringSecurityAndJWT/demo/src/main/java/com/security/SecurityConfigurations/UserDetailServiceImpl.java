package com.security.SecurityConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.Entity.Student;
import com.security.Repository.StudentRepo;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
@Autowired
private StudentRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student=repo.findByUserName(username);
		if(student==null)throw new UsernameNotFoundException("user not found");
	
		return new MyUserDetail(student);
	}

}
