package com.demo.chatMessage.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.ChatMessages;
import com.demo.chatMessage.Model.ChatTopic;
import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Model.UserStatus;
import com.demo.chatMessage.Model.UserStatus.Role;
import com.demo.chatMessage.Model.UserStatus.Status;
import com.demo.chatMessage.Repository.ChatTopicRepo;
import com.demo.chatMessage.Repository.UserRepo;

@Service
public class ChatTopicService {

	@Autowired
	private ChatTopicRepo chatTopicRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	
	
	public ChatTopic createChatTopic(User user,String nameTopic) {
		ChatTopic oldChatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		if(oldChatTopic == null) {
			ChatTopic chatTopic = new ChatTopic();
			UserStatus userStatus = new UserStatus();
			userStatus.setUser(user);
			userStatus.setRole(Role.ADMIN);
			userStatus.setStatus(Status.JOIN);
		    Set<UserStatus> member = new HashSet();
		    List<ChatMessages> listChat = new ArrayList<>();
		    member.add(userStatus);
		    chatTopic.setListChat(listChat);
		    chatTopic.setMembers(member);
		    chatTopic.setNameTopic(nameTopic);
		    return chatTopicRepo.save(chatTopic);
		}
		return oldChatTopic;
	}
	
	public List<ChatMessages> getMessageInTopic(String nameTopic){
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		return chatTopic.getListChat();
	}
	
	public Set<UserStatus> getUserInTopic(String nameTopic){
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		return chatTopic.getMembers();
	}
	
	public ChatTopic addUserInTopic(String nameTopic ,String userId) {
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		User user = userRepo.findByNickName(userId);
		UserStatus userStatus = new UserStatus();
		userStatus.setUser(user);
		userStatus.setRole(Role.USER);
		userStatus.setStatus(Status.JOIN);
		Set<UserStatus> member = chatTopic.getMembers();
		member.add(userStatus);
		chatTopic.setMembers(member);
		return chatTopicRepo.save(chatTopic);
		
	}
	
	public ChatMessages saveChatMessageInTopic(ChatMessages chatmessage,String nameTopic) {
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		List<ChatMessages> listChat = chatTopicRepo.findAllListChatByNameTopic(nameTopic);
		listChat.add(chatmessage);
		chatTopic.setListChat(listChat);
		chatTopicRepo.save(chatTopic);
		return chatmessage;
		
		
	}
}
