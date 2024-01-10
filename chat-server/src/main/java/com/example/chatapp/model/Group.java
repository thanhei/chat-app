package com.example.chatapp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Group {
    private String id;
    private String groupName;
    private String createdBy;
    private String timestamp;
    private List<String> members;
}

