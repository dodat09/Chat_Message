package com.demo.chatMessage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.chatMessage.Model.ChatMessage;
import com.demo.chatMessage.Model.ChatMotification;
import com.demo.chatMessage.Service.ChatMessageService;
import com.demo.chatMessage.Service.ChatTopicService;

@Controller
//@RequestMapping("/")
public class ChatController {

	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private SimpMessagingTemplate messageTemplate;
	
	
	
	
	// các api xử lý one - one 
	@MessageMapping("/chat")
	public void processMessage(@Payload ChatMessage chatMessage) {
		ChatMessage chat = chatMessageService.saveMessage(chatMessage);
		messageTemplate.convertAndSendToUser(chat.getRecipientId()," ",
				                   new ChatMotification(
				                		   chat.getId(),
				                		   chat.getSenderId(),
				                		   chat.getRecipientId(),			                		   chat.getContent()));
	}
	
//	@GetMapping("/messages/{senderId}/{recipientId}")
//	public ResponseEntity<List<ChatMessage>> findChatBySenderIdAndRecipientId(@PathVariable String senderId,@PathVariable String recipientId){
//		return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, recipientId));
//	}
	
	@GetMapping("/messages/{chatId}")
	public ResponseEntity<List<Object>> findChatByChatId(@PathVariable String chatId){
		return ResponseEntity.ok(chatMessageService.findChatMessageByChatId(chatId));
	}
	
	
	// các api xử lý trong topic message
	
	
}
