package com.saga.kafka.repository;


import com.saga.kafka.entity.KafkaMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KafkaMessageLogRepository extends JpaRepository<KafkaMessageLog,Long> {
    List<KafkaMessageLog> findByTopicAndStatus(String topic,String status);
    List<KafkaMessageLog> findByStatus(String status);
    List<KafkaMessageLog> findByTopic(String topic);
    boolean existsByOrderId(String orderId);
}
