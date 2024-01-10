package com.example.chatapp.service;

import com.example.chatapp.model.Chat;
import com.example.chatapp.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServices {
    private final ChatRepository chatRepository;

    public ChatServices(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> findAllChatsByOriginId(String originId){
        return chatRepository.findByOriginOrDestination(originId,originId);
    }

    public Chat createChat(Chat chat){
        return chatRepository.insert(chat);
    }

    public Chat checkExistedChat(String origin, String destination) {
        Chat chat = chatRepository.findByOriginAndDestination(origin, destination);
        if (chat != null) {
            return chat;
        }

        chat = chatRepository.findByOriginAndDestination(destination, origin);
        return chat;
    }


}
