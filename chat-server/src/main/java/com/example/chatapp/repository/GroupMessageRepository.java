package com.example.chatapp.repository;

import com.example.chatapp.model.GroupMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupMessageRepository extends MongoRepository<GroupMessage,String> {
    List<GroupMessage> findAllByGroupId(String groupId);
}
