package com.backend.appback.websocket;

import com.backend.appback.models.SensorData;
import com.backend.appback.service.SensorDataService;
import com.backend.appback.service.ThresholdService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SensorDataWebSocketClient {

    private final SensorDataService sensorDataService;
    private final ObjectMapper objectMapper;
    private final ThresholdService thresholdService;
    private final AlertWebSocketHandler alertWebSocketHandler;

    public SensorDataWebSocketClient(
            SensorDataService sensorDataService,
            ThresholdService thresholdService,
            AlertWebSocketHandler alertWebSocketHandler) {
        this.sensorDataService = sensorDataService;
        this.thresholdService = thresholdService;
        this.alertWebSocketHandler = alertWebSocketHandler;
        this.objectMapper = new ObjectMapper();
    }

    public void connectToBackend1() {
        try {
            StandardWebSocketClient client = new StandardWebSocketClient();

            // Create the WebSocket handler
            WebSocketHandler handler = new TextWebSocketHandler() {
                @Override
                protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                    List<SensorData> sensorDataList = objectMapper.readValue(
                            message.getPayload(),
                            new TypeReference<List<SensorData>>() {}
                    );

                    for (SensorData data : sensorDataList) {
                        sensorDataService.saveSensorData(data);

                        // Vérifie si les données dépassent les seuils
                        if (thresholdService.isValueOutOfBounds(data.getSensorType(), data.getSensorValue())) {
                            alertWebSocketHandler.sendAlert(data);
                        }
                    }
                    System.out.println("Received and processed sensor data: " + sensorDataList);
                }
            };

            // Connect using CompletableFuture
            CompletableFuture<WebSocketSession> future = client.execute(handler, String.valueOf(URI.create("ws://localhost:8081/ws/sensor-data")));


            // Handle the connection
            future.whenComplete((session, throwable) -> {
                if (throwable != null) {
                    System.err.println("Error connecting to WebSocket: " + throwable.getMessage());
                    throwable.printStackTrace();
                } else {
                    System.out.println("Connected to Backend 1 WebSocket successfully.");
                }
            });
        } catch (Exception e) {
            System.err.println("Error initializing WebSocket connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
