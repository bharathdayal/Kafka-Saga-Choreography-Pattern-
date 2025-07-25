package com.saga.kafka.controller;

import com.saga.kafka.entity.KafkaMessageLog;
import com.saga.kafka.repository.KafkaMessageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kafka/messages")
public class KafkaMessageLogController {

    @Autowired
    private  KafkaMessageLogRepository kafkaMessageLogRepository;

    public KafkaMessageLogController(KafkaMessageLogRepository kafkaMessageLogRepository) {
        this.kafkaMessageLogRepository=kafkaMessageLogRepository;
    }

    @GetMapping
    public List<KafkaMessageLog> getAll() {
        return kafkaMessageLogRepository.findAll();
    }

    @GetMapping("/topic/{topic}")
    public List<KafkaMessageLog> getByTopic(@PathVariable String topic) {
        return kafkaMessageLogRepository.findByTopic(topic);
    }

    @GetMapping("/status/{status}")
    public List<KafkaMessageLog> getByStatus(@PathVariable String status) {
        return kafkaMessageLogRepository.findByStatus(status);
    }

    @GetMapping("/topic/{topic}/status/{status}")
    public List<KafkaMessageLog> getByTopicAndStatus(@PathVariable String topic, @PathVariable String status) {
        return kafkaMessageLogRepository.findByTopicAndStatus(topic,status);
    }


}
