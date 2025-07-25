package com.saga.inventory;

import com.saga.kafka.KafkaProducerService;
import com.saga.kafka.repository.PaymentEventLogRepository;
import com.saga.model.Payment;
import com.saga.model.InventoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class InventoryListener {

    @Autowired
    private  KafkaProducerService producer;

    @Autowired
    private final PaymentEventLogRepository paymentEventLogRepository;

    public InventoryListener(KafkaProducerService producer, PaymentEventLogRepository paymentEventLogRepository) {
        this.producer = producer;
        this.paymentEventLogRepository=paymentEventLogRepository;
    }

    @KafkaListener(topics = "payment-processed", groupId = "inventorykafka")
    public void reserveInventory(Payment payment, Acknowledgment ack)  {
        try{
            if (payment.getPaymentId() != null) {
                InventoryEvent event = new InventoryEvent(payment.getOrderId(),payment.getProductId(), "RESERVED",payment.getOrderName(),payment.getPaymentId());
                producer.sendMessage("inventory-stored", event,"RESERVED", event.getOrderId(), payment.getPaymentId());
                ack.acknowledge();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
