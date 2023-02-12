package com.security.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.Entity.Student;
import com.security.Servives.StudentService;

@RestController

public class StudentController {

	@Autowired
	private StudentService service;
	
	
	@PostMapping("/user")
	public ResponseEntity<Student> registerUser(@RequestBody Student student){
		Student st=service.registerUser(student);
		
		return new ResponseEntity<>(st,HttpStatus.CREATED);
		
		
		
	}
	
	
	@PostMapping("/admin")
	public ResponseEntity<Student> registerAdmin(@RequestBody Student student){
		Student st=service.registerAdmin(student);
		
		return new ResponseEntity<>(st,HttpStatus.CREATED);
		
		
		
	}
	@GetMapping("/getAdmin")
	public String hello() {
		return "hi this is admin";
	}
	
	@GetMapping("/getUser")
	public String hellow() {
		return "hi this is user";
	}
	
}
