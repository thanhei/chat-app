package com.example.chatapp.service;

import com.example.chatapp.model.Group;
import com.example.chatapp.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class GroupServices {
    private final GroupRepository groupRepository;

    public GroupServices(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void save(Group group){groupRepository.save(group);}


    public Group findGroupByGroupId(String groupId){return groupRepository.findAllById(groupId);}

    public List<Group> findAllGroupsByMemberContains(String userId){
        return groupRepository.findAllByMembersContains(userId);
    }

    public String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();
        System.out.println("Source: " + i.toString());
        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');
        System.out.println("Date extracted: " + date);
        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";

        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }

        System.out.println("Time extracted: " + time);
        String timeStamp = date + "-" + time;
        return timeStamp;
    }
}
