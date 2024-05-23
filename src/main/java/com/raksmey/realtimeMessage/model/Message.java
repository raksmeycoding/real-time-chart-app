package com.raksmey.realtimeMessage.model;


import com.raksmey.realtimeMessage.common.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private MessageType messageType;
    private String content;
    private String sender;
    private String sessionId;
}
