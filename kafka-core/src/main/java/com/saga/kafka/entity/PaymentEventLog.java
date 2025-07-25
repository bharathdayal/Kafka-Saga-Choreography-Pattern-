package com.saga.kafka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_event_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    private String orderId; // Use orderId as unique identifier
    private String status;
    private LocalDateTime createdAt;


    public PaymentEventLog(String orderId, String status) {
        this.paymentId = UUID.randomUUID();
        this.orderId = orderId;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }


    public PaymentEventLog(UUID paymentId, String orderId, String status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.createdAt = LocalDateTime.now();

    }
}
