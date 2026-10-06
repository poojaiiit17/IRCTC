package com.irctc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @GetMapping("/booking")
    public String bookingPage() {
        return "booking";
    }

    @GetMapping("/payment")
    public String paymentPage() {
        return "payment";
    }

    @GetMapping("/ticket")
    public String ticketPage() {
        return "ticket";
    }

    @GetMapping("/my-bookings")
    public String myBookingsPage() {
        return "my-bookings";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
