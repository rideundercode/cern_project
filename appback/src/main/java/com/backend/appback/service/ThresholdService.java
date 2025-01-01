package com.backend.appback.service;

import com.backend.appback.models.SensorThreshold;
import com.backend.appback.repository.SensorThresholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThresholdService {
    private final SensorThresholdRepository repository;

    public ThresholdService(SensorThresholdRepository repository) {
        this.repository = repository;
    }

    public List<SensorThreshold> getThresholdsForSensor(String sensorType) {
        return repository.findBySensorType(sensorType);
    }

    public boolean isValueOutOfBounds(String sensorType, double value) {
        List<SensorThreshold> thresholds = getThresholdsForSensor(sensorType);
        return thresholds.stream()
                .anyMatch(threshold ->
                        (threshold.getMinThreshold() != null && value < threshold.getMinThreshold()) ||
                                (threshold.getMaxThreshold() != null && value > threshold.getMaxThreshold()));
    }
}