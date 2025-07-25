package com.saga.kafka.repository;

import com.saga.kafka.entity.PaymentEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentEventLogRepository extends JpaRepository<PaymentEventLog,UUID> {

  //  boolean existsByOrderId(String orderId);
    boolean existsByPaymentId(UUID paymentId);
    boolean existsByPaymentIdAndStatus(UUID paymentId, String status);

}
