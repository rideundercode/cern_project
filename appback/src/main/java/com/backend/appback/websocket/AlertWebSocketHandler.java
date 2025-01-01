package com.backend.appback.websocket;

import com.backend.appback.models.SensorData;
import com.backend.appback.service.ThresholdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AlertWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ThresholdService thresholdService;

    public AlertWebSocketHandler(ThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("New WebSocket connection for alerts: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    /**
     * Méthode pour envoyer une alerte aux clients connectés
     */
    public void sendAlert(SensorData sensorData) {
        try {
            String alertMessage = objectMapper.writeValueAsString(sensorData);
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(alertMessage));
                }
            }
            System.out.println("Alert sent: " + alertMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
