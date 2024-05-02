package com.demo.chatMessage.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.ChatMessage;
import com.demo.chatMessage.Repository.ChatMessageRepo;
import com.demo.chatMessage.Repository.ChatTopicRepo;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepo chatMessageRepo;
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private ChatTopicRepo chatTopicRepo;
	
	public ChatMessage saveMessage(ChatMessage chatMessage) {
		//từ chatmessage lấy recipient xem đã là bạn bè chưa hoặc kiểm tra xem chatId này đã tồn tại trước đó hay chưa
		//nếu chưa tồn tại thì tạo mới chatId
			
		String chatId = chatRoomService.getIdChat(chatMessage.getSenderId(),chatMessage.getRecipientId(), true);
		chatMessage.setChatId(chatId);
		chatMessageRepo.save(chatMessage);
		return chatMessage;
	}
	
	
	public List<ChatMessage> findChatMessage(String senderid,String recipientId){
		
		String chatId = chatRoomService.getIdChat(senderid, recipientId, false);
		return chatMessageRepo.findByChatId(chatId);
	}
	
	public List<Object> findChatMessageByChatId(String chatId){
		List<Object> listChat = new ArrayList<>();
		if(StringUtils.isNumeric(chatId)) {
			listChat.add(chatMessageRepo.findByChatId(chatId));
			return listChat;
		}
		 listChat.add(chatTopicRepo.findAllListChatByNameTopic(chatId));
		 return listChat;	
	}
}
