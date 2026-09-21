package com.irctc.config;

import com.irctc.model.Train;
import com.irctc.repository.TrainRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleTrains(TrainRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Train(
                        "12951", "Mumbai Rajdhani",
                        "New Delhi", "Mumbai", 500, 500));

                repository.save(new Train(
                        "12002", "Bhopal Shatabdi",
                        "New Delhi", "Bhopal", 600, 600));

                repository.save(new Train(
                        "12627", "Karnataka Express",
                        "New Delhi", "Bengaluru", 700, 700));
            }
        };
    }
}
