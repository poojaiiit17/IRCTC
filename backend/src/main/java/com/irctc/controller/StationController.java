package com.irctc.controller;

import com.irctc.model.Station;
import com.irctc.repository.StationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
@CrossOrigin(origins = "*")
public class StationController {
    private final StationRepository stationRepository;

    public StationController(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public List<Station> getAll() {
        return stationRepository.findAll();
    }

    @PostMapping
    public Station add(@RequestBody Station station) {
        return stationRepository.save(station);
    }

    @PutMapping("/{id}")
    public Station update(@PathVariable Long id, @RequestBody Station station) {
        Station old = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
        old.setStationCode(station.getStationCode());
        old.setStationName(station.getStationName());
        old.setCity(station.getCity());
        old.setState(station.getState());
        return stationRepository.save(old);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        stationRepository.deleteById(id);
        return "Station deleted successfully";
    }
}