package com.example.chatapp.repository;

import com.example.chatapp.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group,String> {
    Group findAllById(String groupId);

    List<Group> findAllByMembersContains(String userId);
}
