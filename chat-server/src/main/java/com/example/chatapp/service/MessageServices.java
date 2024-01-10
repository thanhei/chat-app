package com.example.chatapp.service;

import com.example.chatapp.model.Message;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServices {
    private final MessageRepository messageRepository;

    public MessageServices(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

public void save(Message message) { this.messageRepository.save(message);}

public List<Message> getAllMessagesByChatId(String chatId) {return this.messageRepository.findByChatId(chatId);}
}
