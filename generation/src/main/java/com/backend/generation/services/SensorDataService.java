package com.backend.generation.services;

import com.backend.generation.models.SensorData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class SensorDataService {

    private final SensorDataWebSocketHandler handler; // Le handler injecté par Spring
    private final Random random = new Random();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Le constructeur avec injection
    public SensorDataService(SensorDataWebSocketHandler handler) {
        this.handler = handler;
    }

    @Scheduled(fixedRate = 2000) // Génère et envoie toutes les 2 secondes
    public void generateAndSendAllSensorData() {
        // Génère toutes les données des capteurs
        List<SensorData> sensorDataList = Arrays.asList(
                generateSensorData("P", 10, 100, "Pa"),
                generateSensorData("V", 0.1, 10, "m³"),
                generateSensorData("n", 0.01, 2, "mol"),
                generateSensorData("T", 250, 350, "K"),
                generateSensorData("I", 0.1, 20, "A") // Correction de "p" -> "I" pour intensité
        );

        // Envoi de la liste complète au WebSocket handler
        handler.sendSensorDataToAll(sensorDataList);

        System.out.println("Generated and sent all sensor data");
    }

    private SensorData generateSensorData(String type, double min, double max, String unit) {
        double sensorValue = min + (max - min) * random.nextDouble();
        String timestamp = LocalDateTime.now().format(formatter);

        return new SensorData(type, sensorValue, unit, timestamp);
    }
}
