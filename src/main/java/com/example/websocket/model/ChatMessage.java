package com.example.websocket.model;

import com.example.websocket.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessage {

    private String roomId;

    private MessageType type;

    private String content;

    private String sender;

}
