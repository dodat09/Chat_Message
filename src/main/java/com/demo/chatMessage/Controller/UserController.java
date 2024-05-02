package com.demo.chatMessage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Service.UserService;

@Controller
//@RequestMapping("/")
public class UserController {
  @Autowired
  private UserService userService;
  
  //endpoint này dùng đẻ connect và lưu User
  @MessageMapping("/user.addUser")
  @SendTo("/user/public")
  private void addUser(@Payload User user) {
	  userService.saveUser(user);
  }
  //đẻ ngắt kết nối với user
  @MessageMapping("/user.disconnectUser")
  @SendTo("/user/public")
  private void diconectUser(@Payload User user) {
	  userService.disconect(user);
  }
  
  //lấy tất cả các object trong đó có thể là user có thể là group
  @GetMapping("/connected/{nickName}")
  public ResponseEntity<List<Object>> findListUser(@PathVariable String nickName){
	  return ResponseEntity.ok(userService.findConnectedUsers(nickName));
  }
  
  @PostMapping("/addfriend")
  public ResponseEntity<User> addFriend(@RequestParam String addId,@RequestParam String friendId){
	  return ResponseEntity.ok(userService.addFriend(addId, friendId));
  }
}
