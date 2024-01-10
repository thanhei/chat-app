package com.example.chatapp.controller;

import com.example.chatapp.model.Group;
import com.example.chatapp.repository.GroupRepository;
import com.example.chatapp.service.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {
    private GroupServices groupServices;
    @Autowired
    public GroupController(GroupServices groupServices) {
        this.groupServices = groupServices;
    }


    @GetMapping("/{groupId}")
    public ResponseEntity<Group> findGroupByGroupId(@PathVariable String groupId){
        return ResponseEntity.ok(groupServices.findGroupByGroupId(groupId));
    }

    @PostMapping("")
    public ResponseEntity<Group> creatGroup(@RequestBody Group group){
        group.setTimestamp(groupServices.generateTimeStamp());
        groupServices.save(group);
        return ResponseEntity.ok(group);
    }


    @GetMapping("/members/{userId}")
    public ResponseEntity<List<Group>> findAllGroupsByMemberContains(@PathVariable String userId){
        return ResponseEntity.ok(groupServices.findAllGroupsByMemberContains(userId));
    }



}
