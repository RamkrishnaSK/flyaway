package com.rsk.simplilearn.flyawayapp.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rsk.simplilearn.flyawayapp.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
	@Query("from Flight where source=:source and destination=:destination and departureDate=:dod ")
	 List<Flight> searchFlights(@Param("source") String from, @Param("destination") String to,
			@Param("dod") LocalDate date);
}
