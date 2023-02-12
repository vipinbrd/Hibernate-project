package com.security.Servives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.Entity.Student;
import com.security.Repository.StudentRepo;
@Service
public class StudentService {
	
	
	@Autowired
	private StudentRepo studentrepo;
	public Student registerUser(Student student) {
		student.setRole("ROLE_USER");
		return studentrepo.save(student);
		
		
		
	}
	public Student registerAdmin(Student student) {
		student.setRole("ROLE_ADMIN");
		return studentrepo.save(student);
		
		
		
	}

}
