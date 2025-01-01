package com.backend.appback.controllers;

import com.backend.appback.models.SensorThreshold;
import com.backend.appback.repository.SensorThresholdRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thresholds")
public class ThresholdController {

    private final SensorThresholdRepository repository;

    public ThresholdController(SensorThresholdRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SensorThreshold> getAllThresholds() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public SensorThreshold updateThreshold(@PathVariable Long id, @RequestBody SensorThreshold threshold) {
        SensorThreshold existingThreshold = repository.findById(id).orElseThrow(() -> new RuntimeException("Threshold not found"));
        existingThreshold.setMinThreshold(threshold.getMinThreshold());
        existingThreshold.setMaxThreshold(threshold.getMaxThreshold());
        return repository.save(existingThreshold);
    }
}