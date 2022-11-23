package com.rsk.simplilearn.flyawayapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rsk.simplilearn.flyawayapp.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
