package com.example.chatapp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Chat {
        @Id
        private String id;
        private String chatName;
        private String origin;
        private String destination;

}
