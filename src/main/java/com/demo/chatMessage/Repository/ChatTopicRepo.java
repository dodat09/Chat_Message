package com.demo.chatMessage.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.chatMessage.Model.ChatMessages;
import com.demo.chatMessage.Model.ChatTopic;
import com.demo.chatMessage.Model.UserStatus;

@Repository
public interface ChatTopicRepo extends MongoRepository<ChatTopic,String>{
    List<ChatMessages> findAllListChatByNameTopic(String nameTopic);
    
    Set<UserStatus> findAllMembersByNameTopic(String nameTopic);
    
    ChatTopic findOneByNameTopic(String nameTopic);
}
