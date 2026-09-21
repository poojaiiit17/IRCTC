package com.irctc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    private Long bookingId;
    private String name;
    private int age;
    private String gender;
    private String seatNumber;
    private String coach;

    public Passenger() {}

    public Passenger(Long bookingId, String name, int age, String gender, String seatNumber, String coach) {
        this.bookingId = bookingId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
        this.coach = coach;
    }

    public Long getPassengerId() { return passengerId; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public String getCoach() { return coach; }
    public void setCoach(String coach) { this.coach = coach; }
}