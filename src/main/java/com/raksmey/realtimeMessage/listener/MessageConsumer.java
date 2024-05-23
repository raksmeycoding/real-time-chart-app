package com.raksmey.realtimeMessage.listener;


import com.raksmey.realtimeMessage.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpUserRegistry userRegistry;

    public MessageConsumer(SimpMessageSendingOperations messagingTemplate, SimpUserRegistry userRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.userRegistry = userRegistry;
    }

    @KafkaListener(topics = "messaging", groupId = "chat")
    public void consume(Message chatMessage) {
        logger.info("Received message from Kafka: " + chatMessage);
        for (SimpUser user : userRegistry.getUsers()) {
            for (SimpSession session : user.getSessions()) {
                if (!session.getId().equals(chatMessage.getSessionId())) {
                    messagingTemplate.convertAndSendToUser(session.getId(), "/topic/public", chatMessage);
                }
            }
        }
    }
}
