package com.demo.chatMessage.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.chatMessage.Model.ChatMessage;

@Repository
public interface ChatMessageRepo extends MongoRepository<ChatMessage,String> {
    List<ChatMessage> findByChatId(String chatId);
}
