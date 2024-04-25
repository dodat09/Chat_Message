package com.demo.chatMessage.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.ChatMessage;
import com.demo.chatMessage.Repository.ChatMessageRepo;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepo chatMessageRepo;
	@Autowired
	private ChatRoomService chatRoomService;
	
	public ChatMessage saveMessage(ChatMessage chatMessage) {
		var chatId = chatRoomService.getIdChat(chatMessage.getSenderId(),chatMessage.getRecipientId(), true)
				    .orElseThrow();
		chatMessage.setChatId(chatId);
		chatMessageRepo.save(chatMessage);
		return chatMessage;
	}
	
	public List<ChatMessage> findChatMessage(String senderid,String recipientId){
		var chatId = chatRoomService.getIdChat(senderid, recipientId, false);
		return chatId.map(chatMessageRepo::findByChatId).orElse(new ArrayList());
	}
}
