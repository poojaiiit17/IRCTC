package com.irctc.service;

import com.irctc.model.*;
import com.irctc.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TrainRepository trainRepository;
    private final PassengerRepository passengerRepository;

    public BookingService(BookingRepository bookingRepository, TrainRepository trainRepository,
                          PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.trainRepository = trainRepository;
        this.passengerRepository = passengerRepository;
    }

    public Booking book(Booking booking) {
        Train train = trainRepository.findById(booking.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        List<Passenger> passengers = booking.getPassengers();
        if (passengers == null || passengers.isEmpty()) {
            Passenger p = new Passenger(null, booking.getPassengerName(), booking.getPassengerAge(),
                    booking.getPassengerGender(), "", "");
            passengers = new ArrayList<>();
            passengers.add(p);
        }

        int count = passengers.size();
        if (count > train.getAvailableSeats()) {
            throw new RuntimeException("Only " + train.getAvailableSeats() + " seats are available");
        }

        double farePerPassenger = getFare(booking.getClassType());
        booking.setAmount(farePerPassenger * count);
        booking.setBookingStatus("PENDING_PAYMENT");
        booking.setPnr("PNR" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());

        Booking saved = bookingRepository.save(booking);

        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);
            p.setBookingId(saved.getBookingId());
            p.setSeatNumber(String.valueOf(i + 1));
            p.setCoach(booking.getClassType() + "1");
            passengerRepository.save(p);
        }
        return saved;
    }

    private double getFare(String classType) {
        if ("2A".equalsIgnoreCase(classType)) return 1800;
        if ("3A".equalsIgnoreCase(classType)) return 1200;
        if ("1A".equalsIgnoreCase(classType)) return 2500;
        return 500;
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if ("CANCELLED".equals(booking.getBookingStatus())) return booking;
        booking.setBookingStatus("CANCELLED");
        return bookingRepository.save(booking);
    }
}