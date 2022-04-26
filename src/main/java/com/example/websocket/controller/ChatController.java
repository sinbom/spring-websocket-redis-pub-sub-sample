package com.example.websocket.controller;

import com.example.websocket.config.RedisPublisher;
import com.example.websocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;

    @MessageMapping("/publish/{id}")
    public void sendMessage(@DestinationVariable String id,
                            @Payload ChatMessage chatMessage) {
        redisPublisher.publish("/topic/" + id, chatMessage);
    }

    @MessageMapping("/join/{id}")
    public void addUser(@DestinationVariable String id, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor
                .getSessionAttributes()
                .put("username", chatMessage.getSender());
        redisPublisher.publish("/topic/" + id, chatMessage);
    }


}
