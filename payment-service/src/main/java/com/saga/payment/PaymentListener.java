package com.saga.payment;

import com.saga.kafka.KafkaProducerService;
import com.saga.model.Order;
import com.saga.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @Autowired
    private final KafkaProducerService producer;

    public PaymentListener(KafkaProducerService producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "order-created", groupId = "payment")
    public void processPayment(Order order) {
        Payment payment = new Payment(order.getOrderId(), "PAID",order.getOrderName());
        producer.sendMessage("payment-processed", payment);
        System.out.println("payment-processed for :" +order.getOrderName());
    }
}
