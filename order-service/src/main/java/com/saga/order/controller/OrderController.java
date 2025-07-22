package com.saga.order.controller;

import com.saga.kafka.KafkaProducerService;
import com.saga.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final KafkaProducerService producer;

    public OrderController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/save")
    public String placeOrder(@RequestBody Order order) {
        producer.sendMessage("order-created", order);
        System.out.println("order-created");
        return "Order placed!";
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("order-created");
        return "test";
    }
}
