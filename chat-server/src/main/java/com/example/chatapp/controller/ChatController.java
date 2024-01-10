package com.example.chatapp.controller;

import com.example.chatapp.model.Chat;
import com.example.chatapp.service.ChatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final ChatServices chatServices;

    @Autowired
    public ChatController(ChatServices chatServices) {
        this.chatServices = chatServices;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Chat>> getAllChatsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(chatServices.findAllChatsByOriginId(userId));
    }

    @GetMapping("")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
//        if (chatServices.checkExistedChat(chat.getOrigin(), chat.getDestination()) != null) {
//            chatServices.createChat(chat);
//            return ResponseEntity.ok(chatServices.checkExistedChat(chat.getOrigin(), chat.getDestination()));
//        } else {
//            return ResponseEntity.ok(chatServices.checkExistedChat(chat.getOrigin(), chat.getDestination()));
//
//        }
            return ResponseEntity.ok(chatServices.checkExistedChat(chat.getOrigin(), chat.getDestination()));

    }

}
