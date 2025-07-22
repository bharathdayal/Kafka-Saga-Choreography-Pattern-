package com.saga.inventory;

import com.saga.kafka.KafkaProducerService;
import com.saga.model.Payment;
import com.saga.model.InventoryEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {

    private final KafkaProducerService producer;

    public InventoryListener(KafkaProducerService producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "payment-processed", groupId = "inventory")
    public void reserveInventory(Payment payment) {
        InventoryEvent event = new InventoryEvent(payment.getOrderId(), "RESERVED");
        producer.sendMessage("inventory-reserved", event);
        System.out.println("inventory-reserved for :"+payment.getOrderName());
    }
}
