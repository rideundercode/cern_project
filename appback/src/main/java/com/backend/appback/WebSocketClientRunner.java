package com.backend.appback;

import com.backend.appback.websocket.SensorDataWebSocketClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebSocketClientRunner implements CommandLineRunner {

    private final SensorDataWebSocketClient webSocketClient;

    public WebSocketClientRunner(SensorDataWebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void run(String... args) throws Exception {
        webSocketClient.connectToBackend1();
    }
}
