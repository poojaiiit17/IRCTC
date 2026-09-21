package com.irctc.service;

import com.irctc.model.Booking;
import com.irctc.model.Train;
import com.irctc.repository.BookingRepository;
import com.irctc.repository.TrainRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TrainRepository trainRepository;

    public BookingService(BookingRepository bookingRepository, TrainRepository trainRepository) {
        this.bookingRepository = bookingRepository;
        this.trainRepository = trainRepository;
    }

    public Booking book(Booking booking) {
        Train train = trainRepository.findById(booking.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        if (train.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        train.setAvailableSeats(train.getAvailableSeats() - 1);
        trainRepository.save(train);

        booking.setBookingStatus("CONFIRMED");
        booking.setPnr("PNR" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("CANCELLED".equals(booking.getBookingStatus())) {
            return booking;
        }

        booking.setBookingStatus("CANCELLED");
        Train train = trainRepository.findById(booking.getTrainId()).orElse(null);
        if (train != null) {
            train.setAvailableSeats(train.getAvailableSeats() + 1);
            trainRepository.save(train);
        }

        return bookingRepository.save(booking);
    }
}
