package com.baeldung.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReactiveWebSocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebSocketApplication.class, args);
    }
}
