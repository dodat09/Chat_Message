package com.demo.chatMessage.Controller;

import java.util.Set;

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
import com.demo.chatMessage.Model.UserStatus;
import com.demo.chatMessage.Service.ChatTopicService;
import com.demo.chatMessage.Service.UserService;

@RestController
@RequestMapping("/")
public class ChatTopicController {
    @Autowired
    private ChatTopicService chatTopicService;
    @Autowired
    private UserService userService;
    // tạo mới một topic 
    @PostMapping("/createTopic")
    public ChatTopic createChat(@RequestParam String nameTopic,@RequestParam String userId) {
    	// tìm user tạo topic và trao quyền admin
    	User user = userService.getUserById(userId);
    	return chatTopicService.createChatTopic(user, nameTopic);
    }
    
    //lấy tất cả các message của topic ra để hiển thị 
//    @GetMapping("/messages/{chatId}")
//    public ResponseEntity<List<ChatMessages>> getGroupChat(@PathVariable String chatId) {
//       return ResponseEntity.ok(chatTopicService.getMessageInTopic(chatId));
//    }
    
    //lấy tất cả các user trong topic này
    @GetMapping("/userInTopic")
    public ResponseEntity<Set<UserStatus>> getUserChat(@RequestParam String nameTopic){
    	return ResponseEntity.ok(chatTopicService.getUserInTopic(nameTopic));
    }
    
    
//    @GetMapping("/groups")
//    public ResponseEntity<List<ChatTopic>> getAllGroupByUser(@RequestParam String userId){
//    	return ResponseEntity.ok(chatTopicService.getAllGroupByUser(userId));
//    }
    
    //thêm 1 user vào topic
    @PostMapping("/addUserInTopic")
    public ResponseEntity<ChatTopic> addUser(@RequestParam String nameTopic,@RequestParam String userId) {
    	return ResponseEntity.ok(chatTopicService.addUserInTopic(nameTopic, userId));
    }
    
    //gửi tin nhắn trông group
    @MessageMapping("/app/group.sendMessage")
    @SendTo("/message/{nameTopic}")
    public ChatMessages sendMessageInTopic(@Payload ChatMessages chatmessage ,@PathVariable String nameTopic ) {
    	 return chatTopicService.saveChatMessageInTopic(chatmessage, nameTopic);
    }
    
    
}
