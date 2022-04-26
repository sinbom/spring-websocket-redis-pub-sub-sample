package com.example.websocket.config;

import com.example.websocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, ChatMessage> redisTemplate;

    public void publish(String topic, ChatMessage chatMessage) {
        redisTemplate.convertAndSend(topic, chatMessage);
    }

}
