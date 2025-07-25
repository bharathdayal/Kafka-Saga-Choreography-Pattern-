package com.saga.kafka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @Lob
    private String payload;

    private String status;

    private LocalDateTime timestamp;

    private String orderId;

    private UUID paymentId;

    public KafkaMessageLog(String topic, String payload, String status, LocalDateTime timestamp, String orderId, UUID paymentId) {
        this.topic=topic;
        this.payload=payload;
        this.status=status;
        this.timestamp=timestamp;
        this.orderId =orderId;
        this.paymentId = paymentId;
    }

}
