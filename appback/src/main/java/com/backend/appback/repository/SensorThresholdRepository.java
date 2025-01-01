package com.backend.appback.repository;

import com.backend.appback.models.SensorThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorThresholdRepository extends JpaRepository<SensorThreshold, Long> {
    List<SensorThreshold> findBySensorType(String sensorType);
}