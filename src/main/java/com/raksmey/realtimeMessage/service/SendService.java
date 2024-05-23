package com.raksmey.realtimeMessage.service;

import com.raksmey.realtimeMessage.model.Message;

public interface SendService {
    public void send(String topic, Message message);
}
