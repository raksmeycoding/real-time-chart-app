package com.raksmey.realtimeMessage.service;


import com.raksmey.realtimeMessage.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendServiceImpl implements SendService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public SendServiceImpl(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, Message message) {
        this.kafkaTemplate.send(topic, message);
    }
}
