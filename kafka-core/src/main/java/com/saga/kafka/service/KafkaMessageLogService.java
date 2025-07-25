package com.saga.kafka.service;

import com.saga.kafka.entity.KafkaMessageLog;
import com.saga.kafka.repository.KafkaMessageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class KafkaMessageLogService {

    @Autowired
    private  KafkaMessageLogRepository kafkaMessageLogRepository;

    public KafkaMessageLog saveLog(String topic, String payload, String status, LocalDateTime timestamp, String orderId, UUID paymentId) {
        KafkaMessageLog log = new KafkaMessageLog(topic,payload,status,timestamp,orderId,paymentId);
        log.setTopic(topic);
        log.setPayload(payload);
        log.setStatus(status);
        log.setTimestamp(timestamp);
        log.setOrderId(orderId);
        log.setPaymentId(paymentId);

        return kafkaMessageLogRepository.save(log);
    }
}
