package com.demo.chatMessage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Service.UserService;

@Controller
//@RequestMapping("/")
public class UserController {
  @Autowired
  private UserService userService;
  
  @MessageMapping("/user.addUser")
  @SendTo("/user/public")
  private void addUser(@Payload User user) {
	  userService.saveUser(user);
  }
  
  @MessageMapping("/user.disconnectUser")
  @SendTo("/user/public")
  private void diconectUser(@Payload User user) {
	  userService.disconect(user);
  }
  
  @GetMapping("/users")
  public ResponseEntity<List<User>> findListUser(){
	  return ResponseEntity.ok(userService.findConnectedUsers());
  }
}
