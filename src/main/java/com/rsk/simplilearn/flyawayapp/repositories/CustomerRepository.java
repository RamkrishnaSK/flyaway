package com.rsk.simplilearn.flyawayapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rsk.simplilearn.flyawayapp.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	public List<Customer> findByEmail(String email);  
}
