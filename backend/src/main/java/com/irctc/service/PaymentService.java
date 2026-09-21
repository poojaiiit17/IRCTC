package com.irctc.service;

import com.irctc.model.*;
import com.irctc.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final TrainRepository trainRepository;
    private final PassengerRepository passengerRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository,
                          TrainRepository trainRepository, PassengerRepository passengerRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.trainRepository = trainRepository;
        this.passengerRepository = passengerRepository;
    }

    public Payment makePayment(Payment payment) {
        Booking booking = bookingRepository.findById(payment.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!"PENDING_PAYMENT".equals(booking.getBookingStatus())) {
            throw new RuntimeException("Booking is not waiting for payment");
        }

        int passengerCount = passengerRepository.findByBookingId(booking.getBookingId()).size();
        Train train = trainRepository.findById(booking.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        if (passengerCount > train.getAvailableSeats()) {
            throw new RuntimeException("Seats are no longer available");
        }

        train.setAvailableSeats(train.getAvailableSeats() - passengerCount);
        trainRepository.save(train);

        payment.setAmount(booking.getAmount());
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase());
        payment.setPaymentDate(LocalDateTime.now());

        booking.setBookingStatus("CONFIRMED");
        bookingRepository.save(booking);
        return paymentRepository.save(payment);
    }
}