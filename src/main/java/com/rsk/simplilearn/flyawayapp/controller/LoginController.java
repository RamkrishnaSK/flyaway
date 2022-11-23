package com.rsk.simplilearn.flyawayapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rsk.simplilearn.flyawayapp.entities.Customer;
import com.rsk.simplilearn.flyawayapp.repositories.CustomerRepository; 

@RestController
public class LoginController {

	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		String hashPwd = this.passwordEncoder.encode(customer.getPwd());
		customer.setPwd(hashPwd);
		this.custRepo.save(customer);
		return new ResponseEntity<String>("Registration Success!", HttpStatus.CREATED);
	}

}
