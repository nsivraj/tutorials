package com.baeldung.reactive.serversentevents.server.controllers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/sse-server")
public class ServerController {

    @Autowired
    StockTransactionService stockTransactionService;

    List<Stock> stockList = new ArrayList<>();
    List<String> stockNames = Arrays.asList("mango,banana,guava,infinity".split(","));

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            createRandomStock();
            stockList.forEach(System.out::println);
        };
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> ServerSentEvent.<String> builder()
                .id(String.valueOf(sequence))
                .event("periodic-event")
                .data("SSE - " + LocalTime.now().toString())
                .build());
    }

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> "Flux - " + LocalTime.now().toString());
    }

    @GetMapping(path = "/stream-json-stocks", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<StockTransaction> stockTransactionAsJsonEvents(){
        return stockTransactionService.getStockTransactions();
    }

    @GetMapping(path = "/stream-sse-stocks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockTransaction> stockTransactionAsSSE(){
        return stockTransactionService.getStockTransactions();
    }

    @Service
    class StockTransactionService {
        Flux<StockTransaction> getStockTransactions() {
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
            interval.subscribe((i) -> stockList.forEach(stock -> stock.setPrice(changePrice(stock.getPrice()))));

            Flux<StockTransaction> stockTransactionFlux = Flux.fromStream(Stream.generate(() -> new StockTransaction(getRandomUser(), getRandomStock(), new Date())));
            return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    class Stock {
        String name;
        float price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    class StockTransaction {
        String user;
        Stock stock;
        Date when;
    }

    void createRandomStock() {
        stockNames.forEach(stockName -> {
            stockList.add(new Stock(stockName, generateRandomStockPrice()));
        });
    }

    float generateRandomStockPrice() {
        float min = 30;
        float max = 50;
        return min + roundFloat(new Random().nextFloat() * (max - min));
    }

    float changePrice(float price) {
        return roundFloat(Math.random() >= 0.5 ? price * 1.05f : price * 0.95f);
    }

    String getRandomUser() {
        String users[] = "adam,tom,john,mike,bill,tony".split(",");
        return users[new Random().nextInt(users.length)];
    }

    Stock getRandomStock() {
        return stockList.get(new Random().nextInt(stockList.size()));
    }

    float roundFloat(float number) {
        return Math.round(number * 100.0) / 100.0f;
    }
}

