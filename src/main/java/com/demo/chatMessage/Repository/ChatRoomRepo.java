package com.demo.chatMessage.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.chatMessage.Model.ChatRoom;

@Repository
public interface ChatRoomRepo extends MongoRepository<ChatRoom,String>{

	ChatRoom findBySenderIdAndRecipientId(String senderId,String recipientId);
	
	ChatRoom findByChatId(String chatId);
}
