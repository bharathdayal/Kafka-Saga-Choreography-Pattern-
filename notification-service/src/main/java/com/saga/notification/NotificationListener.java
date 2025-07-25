package com.saga.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.kafka.entity.KafkaMessageLog;
import com.saga.kafka.service.KafkaMessageLogService;
import com.saga.model.InventoryEvent;
import com.saga.model.Order;
import com.saga.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class NotificationListener {

    @Autowired
    private KafkaMessageLogService logService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "inventory-stored", groupId = "notificationkafka")
    public void notifyUser(InventoryEvent event, Acknowledgment ack) {
        try {
            System.out.println("Notification: Order " + event.getOrderId() + " is reserved, Status : "+ event.getStatus());
            InventoryEvent event1 = new InventoryEvent(event.getOrderId(),event.getProductId(), "COMPLETED",event.getOrderName(),event.getPaymentId());
            String payload = convertToJson(event1);
            KafkaMessageLog log = new KafkaMessageLog("order-completed", payload, "COMPLETED", LocalDateTime.now(),event.getOrderId(),event.getPaymentId());
            ack.acknowledge();
            logService.saveLog(log.getTopic(),log.getPayload(),log.getStatus(),log.getTimestamp(),event.getOrderId(), null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private String convertToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "{\"error\":\"serialization failed\"}";
        }
    }
}
