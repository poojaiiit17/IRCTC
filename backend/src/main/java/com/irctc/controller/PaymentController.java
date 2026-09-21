package com.irctc.controller;

import com.irctc.model.Payment;
import com.irctc.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment pay(@RequestBody Payment payment) {
        return paymentService.makePayment(payment);
    }
}
