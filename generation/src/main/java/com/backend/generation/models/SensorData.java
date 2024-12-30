package com.backend.generation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    private String sensorType;  // Type de capteur : P, V, n, T, p
    private double sensorValue;       // Valeur générée
    private String unit;        // Unité (e.g., Pa, m³, mol, K, bar)
    private String timestamp;   // Timestamp de la génération
}