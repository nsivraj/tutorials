package com.baeldung.websocket;

import java.net.URI;
import java.time.Duration;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

public class ReactiveJavaClientWebSocket {
	public static void main(String[] args) throws InterruptedException {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
          URI.create("wss://localhost:8443/event-emitter"),
          session -> session.send(
            Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
            .thenMany(session.receive()
              .map(WebSocketMessage::getPayloadAsText)
              .log())
            .then())
            .block(Duration.ofSeconds(10L));
    }

}
