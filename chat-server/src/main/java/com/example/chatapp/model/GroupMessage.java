package com.example.chatapp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("group messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupMessage {
    private String id;
    private String groupId;
    private String senderId;
    private String content;
    private String timestamp;

}
