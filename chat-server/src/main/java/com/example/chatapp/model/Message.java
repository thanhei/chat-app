package com.example.chatapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String content;
    private String timestamp;
    private Status status;
    private String chatId;
}
