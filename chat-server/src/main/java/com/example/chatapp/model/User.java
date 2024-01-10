package com.example.chatapp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String mail;
    private String phoneNumber;



}
