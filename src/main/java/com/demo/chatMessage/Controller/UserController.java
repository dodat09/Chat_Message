package com.demo.chatMessage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Model.UserDetail;
import com.demo.chatMessage.Service.UserService;

@Controller
@RequestMapping("/")
@Transactional
public class UserController {
  @Autowired
  private UserService userService;
  
  //endpoint này dùng đẻ connect và lưu User
  @MessageMapping("/user.addUser/{nickName}")
  @SendTo("/user/public")
  private void addUser(@PathVariable String nickname) {
	  userService.saveUser(nickname);
  }
  
  //đẻ ngắt kết nối với user
  @MessageMapping("/user.disconnectUser")
  @SendTo("/user/public")
  private void diconectUser(@Payload User user) {
	  userService.disconect(user);
  }
  //chuyen hương đến trang login
//  @GetMapping("/login")
//  public ResponseEntity<String> intoLogin(Model model) throws URISyntaxException{
//	  String uri = "/login.html";
//	  URI uriLogin = new URI(uri);
//	 return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
//			 .location(uriLogin).build();
//  }
  @GetMapping("/login")
  public String loginUser() {
	  return "login";
  }
  
  //tạo tài khoản thông tin user
  @PostMapping("/sighup")
  public ResponseEntity<User> sighUpUser(@RequestParam String username ,@RequestParam String password) {
	 return  ResponseEntity.ok(userService.sighUp(username,password));
  }
  
  //lấy tất cả các object trong đó có thể là user có thể là group
  @GetMapping("/connected/{username}")
  public ResponseEntity<List<Object>> findListUser(@PathVariable String username){
	  return ResponseEntity.ok(userService.findConnectedUsers(username));
  }
  
  @PostMapping("/addfriend")
  public ResponseEntity<User> addFriend(@RequestParam String addId,@RequestParam String friendId){
	  return ResponseEntity.ok(userService.addFriend(addId, friendId));
  }
  
  @GetMapping("/verify")
  public ResponseEntity<Object> veryfyLogin(Authentication authentication){
	  Object user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  return ResponseEntity.ok(user);
  }
}
