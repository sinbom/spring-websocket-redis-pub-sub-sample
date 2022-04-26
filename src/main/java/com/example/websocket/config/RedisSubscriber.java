package com.example.websocket.config;

import com.example.websocket.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String subscribeMessage = new String(message.getBody());
        ChatMessage chatMessage = objectMapper.readValue(subscribeMessage, ChatMessage.class);

        simpMessagingTemplate.convertAndSend("/topic/" + chatMessage.getRoomId(), chatMessage);
    }

}
