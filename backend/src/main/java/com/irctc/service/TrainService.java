package com.irctc.service;

import com.irctc.model.Train;
import com.irctc.repository.TrainRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public List<Train> search(String source, String destination) {
        return trainRepository.findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);
    }

    public Train addTrain(Train train) {
        return trainRepository.save(train);
    }

    public void deleteTrain(Long id) {
        trainRepository.deleteById(id);
    }
}
