package com.irctc.controller;

import com.irctc.model.Passenger;
import com.irctc.repository.PassengerRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "*")
public class PassengerController {
    private final PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @GetMapping("/booking/{bookingId}")
    public List<Passenger> getByBooking(@PathVariable Long bookingId) {
        return passengerRepository.findByBookingId(bookingId);
    }

    @PostMapping
    public Passenger add(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }
}