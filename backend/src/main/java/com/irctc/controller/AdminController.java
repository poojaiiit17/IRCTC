package com.irctc.controller;

import com.irctc.model.Booking;
import com.irctc.model.Train;
import com.irctc.repository.BookingRepository;
import com.irctc.repository.TrainRepository;
import com.irctc.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    private final UserRepository userRepository;
    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;

    public AdminController(UserRepository userRepository, TrainRepository trainRepository,
                           BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.trainRepository = trainRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/stats")
    public Map<String, Long> stats() {
        return Map.of(
                "users", userRepository.count(),
                "trains", trainRepository.count(),
                "bookings", bookingRepository.count()
        );
    }

    @GetMapping("/bookings")
    public List<Booking> allBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/users")
    public List<com.irctc.model.User> allUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/trains/{id}")
    public Train updateTrain(@PathVariable Long id, @RequestBody Train train) {
        Train old = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));
        old.setTrainNumber(train.getTrainNumber());
        old.setTrainName(train.getTrainName());
        old.setSource(train.getSource());
        old.setDestination(train.getDestination());
        old.setTotalSeats(train.getTotalSeats());
        old.setAvailableSeats(train.getAvailableSeats());
        return trainRepository.save(old);
    }
}