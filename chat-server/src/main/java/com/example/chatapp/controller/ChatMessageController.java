package com.example.chatapp.controller;

import com.example.chatapp.model.Message;
import com.example.chatapp.service.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat/messages")
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageServices messageServices;


    public ChatMessageController(SimpMessagingTemplate messagingTemplate, MessageServices messageServices) {
        this.messagingTemplate = messagingTemplate;
        this.messageServices=messageServices;
    }

    @MessageMapping("/chat")
    public void sendPrivateMessages(@RequestBody Message message)
    {
        try {
                    messageServices.save(message);
                    messagingTemplate.convertAndSendToUser(message.getReceiver(),"message", message);

        } catch (Exception e) { 
                    e.printStackTrace();
        }
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getAllMessageByChatID(@PathVariable String chatId){

            return ResponseEntity.ok(messageServices.getAllMessagesByChatId(chatId));
    }




}
