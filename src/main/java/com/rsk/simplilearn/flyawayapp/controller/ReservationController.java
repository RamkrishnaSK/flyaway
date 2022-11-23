package com.rsk.simplilearn.flyawayapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsk.simplilearn.flyawayapp.dto.CreateReservationRequest;
import com.rsk.simplilearn.flyawayapp.entities.Customer;
import com.rsk.simplilearn.flyawayapp.entities.Flight;
import com.rsk.simplilearn.flyawayapp.entities.Passenger;
import com.rsk.simplilearn.flyawayapp.entities.Reservation;
import com.rsk.simplilearn.flyawayapp.repositories.CustomerRepository;
import com.rsk.simplilearn.flyawayapp.repositories.FlightRepository;
import com.rsk.simplilearn.flyawayapp.repositories.PassengerRepository;
import com.rsk.simplilearn.flyawayapp.repositories.ReservationRepository;

@RestController
@CrossOrigin
public class ReservationController {

	@Autowired
	private FlightRepository flightRepo;

	@Autowired
	private PassengerRepository passengerRepo;

	@Autowired
	private ReservationRepository resRepo;

	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/flights/search")
	public ResponseEntity<List<Flight>> findFlights(@RequestParam("source") String from,
			@RequestParam("destination") String to,
			@RequestParam("dateOfDeparture") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		List<Flight> flights = this.flightRepo.searchFlights(from, to, date);
		return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
	}

	@PostMapping("/flights/")
	public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight) {
		return new ResponseEntity<Flight>(this.flightRepo.save(flight), HttpStatus.CREATED);
	}

	@PostMapping("/reservation")
	@Transactional
	public ResponseEntity<String> saveReservation(@RequestBody CreateReservationRequest request) {

		Flight flight = this.flightRepo.findById(request.getFlightId()).get();

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setEmail(request.getEmail());
		passenger.setPhone(request.getPhone());
		passenger.setCardNumber(request.getCardNumber());
		passenger.setExpDate(request.getExpDate());
		passenger.setSecurityCode(request.getSecurityCode());
		Passenger savedPassenger = this.passengerRepo.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		this.resRepo.save(reservation);

		return new ResponseEntity<String>("Booking Successfull!! Happy Journey!!", HttpStatus.CREATED);

	}

	@GetMapping("/reservation/{resId}")
	public ResponseEntity<Reservation> findReservation(@PathVariable("resId") int id) {
		return new ResponseEntity<Reservation>(this.resRepo.findById(id).get(), HttpStatus.OK);
	}

	@PostMapping("/changePassword/{emailz}")
	public ResponseEntity<String> changePwd(@PathVariable("emailz") String email , @RequestBody String newPassword) {

		List<Customer> customer = this.custRepo.findByEmail(email);
		Customer customerz = customer.get(0);
		
		String hashPwd = this.passwordEncoder.encode(newPassword);
		customerz.setPwd(hashPwd);

		this.custRepo.save(customerz);
		return new ResponseEntity<String>("Password Changed Successfully..!!", HttpStatus.OK);

	}
 
}
