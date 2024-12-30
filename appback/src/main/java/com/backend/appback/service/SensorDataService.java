package com.backend.appback.service;

import com.backend.appback.models.SensorData;
import com.backend.appback.repository.SensorDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorDataService {

    private final SensorDataRepository repository;

    public SensorDataService(SensorDataRepository repository) {
        this.repository = repository;
    }

    public void saveSensorData(SensorData data) {
        repository.save(data);
    }

    public List<SensorData> getAllSensorData() {
        return repository.findAll();
    }
}
