package com.demo.chatMessage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.chatMessage.Model.ChatMessages;
import com.demo.chatMessage.Model.ChatTopic;
import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Service.ChatTopicService;
import com.demo.chatMessage.Service.UserService;

@RestController
@RequestMapping("/")
public class ChatTopicController {
    @Autowired
    private ChatTopicService chatTopicService;
    @Autowired
    private UserService userService;
    
    @PostMapping("/createTopic")
    public ChatTopic createChat(@RequestParam String nameTopic,@RequestParam String userId) {
    	User user = userService.getUserById(userId);
    	return chatTopicService.createChatTopic(user, nameTopic);
    }
    
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessages>> getGroupChat(@RequestParam String nameTopic) {
       return ResponseEntity.ok(chatTopicService.getMessageInTopic(nameTopic));
    }
    
    @PostMapping("/addUserInTopic")
    public ResponseEntity<ChatTopic> addUser(@RequestParam String nameTopic,@RequestParam String userId) {
    	return ResponseEntity.ok(chatTopicService.addUserInTopic(nameTopic, userId));
    }
    
    @MessageMapping("/app/group.sendMessage")
    @SendTo("/topic/{nameTopic}")
    public ChatMessages sendMessageInTopic(@Payload ChatMessages chatmessage ,@PathVariable String nameTopic ) {
    	 return chatTopicService.saveChatMessageInTopic(chatmessage, nameTopic);
    }
    
    
}
