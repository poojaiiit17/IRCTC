package com.irctc.service;

import com.irctc.model.Payment;
import com.irctc.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment makePayment(Payment payment) {
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId("TXN-" +
                UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase());
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}
