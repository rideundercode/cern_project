package com.backend.appback.controllers;

import com.backend.appback.models.SensorData;
import com.backend.appback.service.SensorDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SensorDataController {

    private final SensorDataService service;

    public SensorDataController(SensorDataService service) {
        this.service = service;
    }

    @GetMapping("/api/sensor-data")
    public List<SensorData> getAllSensorData(@RequestParam(required = false) String sensorType) {
        List<SensorData> allData = service.getAllSensorData();
        if (sensorType != null) {
            return allData.stream()
                    .filter(data -> data.getSensorType().equalsIgnoreCase(sensorType))
                    .toList();
        }
        return allData;
    }
}
