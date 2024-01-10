package com.example.chatapp.controller;

import com.example.chatapp.model.Group;
import com.example.chatapp.model.GroupMessage;
import com.example.chatapp.model.Message;
import com.example.chatapp.service.GroupMessageServices;
import com.example.chatapp.service.GroupServices;
import com.example.chatapp.service.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group/messages")
public class GroupMessageController {

    private SimpMessagingTemplate messagingTemplate;

    private GroupServices groupServices;

    private GroupMessageServices groupMessageServices;

    public GroupMessageController(SimpMessagingTemplate messagingTemplate, GroupServices groupServices, GroupMessageServices groupMessageServices) {
        this.messagingTemplate = messagingTemplate;
        this.groupServices = groupServices;
        this.groupMessageServices = groupMessageServices;
    }

    @MessageMapping("/group")
    @SendTo("/chatroom/public")
    public GroupMessage sendGroupMessage(@RequestBody GroupMessage groupMessage){
        try {
            groupMessage.setTimestamp(groupMessageServices.generateTimeStamp());
            groupMessageServices.save(groupMessage);
            return groupMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<GroupMessage>> getAllGroupMessageByGroupId(@PathVariable String groupId){
        return  ResponseEntity.ok(groupMessageServices.getAllMessageByGroupId(groupId));
    }


    @PostMapping("")
    public ResponseEntity<Group> creatGroup(@RequestBody Group group){
     group.setTimestamp(groupServices.generateTimeStamp());
     groupServices.save(group);
     return ResponseEntity.ok(group);
    }


}
