package com.saga.order.controller;

import com.saga.kafka.KafkaProducerService;
import com.saga.kafka.repository.KafkaMessageLogRepository;
import com.saga.model.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final KafkaProducerService producer;



    public OrderController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/save")
    //@CircuitBreaker(name = "orderCB", fallbackMethod = "fallbackPlaceOrder")
    public String placeOrder(@RequestBody Order order)  {

        producer.sendMessage("order-created", order,"PENDING", order.getOrderId(),null);
        System.out.println("order-created");
        return "Order placed!";
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("order-created");
        return "test";
    }
    // Fallback method must match controller method's parameters and return type
    public String fallbackPlaceOrder(Order order, Throwable throwable) {
        System.err.println("[FALLBACK] Order failed: " + order.getOrderId());
        System.err.println("Reason: " + throwable.getMessage());
        return "Temporarily unable to place order. Please try again later.";
    }
}
