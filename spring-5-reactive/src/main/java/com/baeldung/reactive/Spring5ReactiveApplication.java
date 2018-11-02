package com.baeldung.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Spring5ReactiveApplication{

    public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveApplication.class, args);
    }

}
