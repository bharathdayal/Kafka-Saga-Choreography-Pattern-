package com.saga.notification;

import com.saga.model.InventoryEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @KafkaListener(topics = "inventory-reserved", groupId = "notification")
    public void notifyUser(InventoryEvent event) {
        System.out.println("Notification: Order " + event.getOrderId() + " is reserved");
    }
}
