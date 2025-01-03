package com.backend.generation.config;

import com.backend.generation.services.SensorDataWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public SensorDataWebSocketHandler handler;

    public WebSocketConfig(SensorDataWebSocketHandler handler){
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers (WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/ws/sensor-data").setAllowedOrigins("*");
    }
}
