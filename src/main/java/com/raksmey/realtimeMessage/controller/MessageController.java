package com.raksmey.realtimeMessage.controller;

import com.raksmey.realtimeMessage.model.Message;
import com.raksmey.realtimeMessage.service.SendService;
import org.apache.kafka.clients.producer.internals.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SendService sendService;
    private final SimpMessageSendingOperations messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(SendService sendService, SimpMessageSendingOperations messagingTemplate) {
        this.sendService = sendService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send-message")
    public void sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        chatMessage.setSessionId(headerAccessor.getSessionId());
        sendService.send("messaging", chatMessage);
        logger.info("Sending message to /topic/public: " + chatMessage);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
        logger.info("Message sent to /topic/public: " + chatMessage);
    }

    @MessageMapping("/chat.add-user")
    @SendTo("/topic/public")
    public Message addUser(
            @Payload Message chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        }

        return chatMessage;
    }
}