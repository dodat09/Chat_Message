package com.demo.chatMessage.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.chatMessage.Model.ChatRoom;

@Repository
public interface ChatRoomRepo extends MongoRepository<ChatRoom,String>{

	Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId,String recipientId);
}
