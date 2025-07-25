package com.saga.payment.controller;

import com.saga.kafka.KafkaProducerService;
import com.saga.model.Payment;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {


    private final KafkaProducerService producerService;

    public PaymentController(KafkaProducerService producerService) {
        this.producerService=producerService;
    }

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody Payment payment) {


        producerService.sendMessage("payment-processed", payment,"PAID",payment.getOrderId(), payment.getPaymentId());
        return ResponseEntity.ok("Payment processed for orderId=" + payment.getOrderId());
    }
}
