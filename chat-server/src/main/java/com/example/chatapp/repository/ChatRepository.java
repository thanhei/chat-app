package com.example.chatapp.repository;

import com.example.chatapp.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat,Long> {

    List<Chat> findByOriginOrDestination(String origin,String destination);

    Chat findByOriginAndDestination(String origin,String destination);
}
