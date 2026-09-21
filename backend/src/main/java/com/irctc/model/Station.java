package com.irctc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    @Column(unique = true, nullable = false)
    private String stationCode;

    @Column(nullable = false)
    private String stationName;

    private String city;
    private String state;

    public Station() {}

    public Station(String stationCode, String stationName, String city, String state) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.city = city;
        this.state = state;
    }

    public Long getStationId() { return stationId; }
    public String getStationCode() { return stationCode; }
    public void setStationCode(String stationCode) { this.stationCode = stationCode; }
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}