package com.rsk.simplilearn.flyawayapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rsk.simplilearn.flyawayapp.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

}
