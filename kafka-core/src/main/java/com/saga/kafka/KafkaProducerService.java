package com.saga.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.kafka.entity.KafkaMessageLog;
import com.saga.kafka.entity.PaymentEventLog;
import com.saga.kafka.repository.KafkaMessageLogRepository;
import com.saga.kafka.repository.PaymentEventLogRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Nullable;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaMessageLogRepository kafkaMessageLogRepository;

    private final PaymentEventLogRepository paymentEventLogRepository;


    @Autowired
    private ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate,KafkaMessageLogRepository kafkaMessageLogRepository,PaymentEventLogRepository paymentEventLogRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaMessageLogRepository = kafkaMessageLogRepository;
        this.paymentEventLogRepository = paymentEventLogRepository;
    }

    //@CircuitBreaker(name = "kafkaProducerCB", fallbackMethod = "fallbackSend")
    public void sendMessage(String topic, Object message,String status,String messageKey,@Nullable UUID paymentId) {
        String payload = convertToJson(message);
        KafkaMessageLog log = new KafkaMessageLog(topic, payload, status, LocalDateTime.now(),messageKey,paymentId);

        if (paymentId != null) {
            log.setPaymentId(paymentId); // Only if UUID is passed
        }

        if (paymentEventLogRepository.existsByPaymentIdAndStatus(paymentId,status)) {
            System.out.println("Duplicate payment event skipped for orderId: " + paymentId);
            return;
        }

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, messageKey,message);

        future.thenAccept(result -> {
            System.out.printf("Kafka send success. Topic=%s, Partition=%d, Offset=%d%n",
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());

            try {
                kafkaMessageLogRepository.save(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).exceptionally(ex -> {
            System.err.println("Kafka send failed: " + ex.getMessage());
            log.setStatus("FAILED");
            kafkaMessageLogRepository.save(log);
            return null;
        });

    }

    // Fallback method if Kafka is down
    public void fallbackSend(String topic, Object message, Throwable throwable) {
        System.err.println("[FALLBACK] Failed to send message to topic [" + topic + "]: " + message);
        System.err.println("Reason: " + throwable.getMessage());
        System.out.println("Reason: " + throwable.getMessage());

    }

    private String convertToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "{\"error\":\"serialization failed\"}";
        }
    }
}
