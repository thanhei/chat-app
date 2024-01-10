package com.example.chatapp;

import com.example.chatapp.model.Chat;
import com.example.chatapp.model.Group;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.ChatRepository;
import com.example.chatapp.repository.GroupRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ChatAppApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    GroupRepository groupRepository;

    public static void main(String[] args) {

        SpringApplication.run(ChatAppApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
//            Group group=new Group();
//            group.setGroupName("group2");
//            group.setCreatedBy("user123");
//            group.setTimestamp("");
//            List<String> members=new ArrayList<>();
//            members.add("user123");
//            group.setMembers(members);
//            groupRepository.save(group);
//        Chat chat=new Chat();
//        chat.setChatName("Me and U");
//        chat.setOrigin("user123");
//        chat.setDestination("thanhei");
//        chatRepository.save(chat);
//        User user=new User();
//        user.setUsername("thanhei");
//        user.setPassword(passwordEncoder.encode("thanhei"));
//        user.setRole("USER");
//        userRepository.save(user);

    }
}
