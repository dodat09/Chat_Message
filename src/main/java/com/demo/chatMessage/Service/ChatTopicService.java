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
import com.demo.chatMessage.Repository.UserStatusRepo;

@Service
public class ChatTopicService {

	@Autowired
	private ChatTopicRepo chatTopicRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserStatusRepo userStatusRepo;
	
	
	 //tạo một topic mới với user và tên khởi tại
	public ChatTopic createChatTopic(User user,String nameTopic) {
		 //lấy chatTopic thong qua nameTopic
		ChatTopic oldChatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		//kiểm tra xem chatTopic đã tôn tại hay chưa
		if(oldChatTopic == null) {
			// nếu chưa tồn tại ta xet lại tất cả các thuộc tính của chatTopic
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
		    //lấy ra list chatID của user
		    Set<String> chatId = user.getChatId();
		    chatId.add(nameTopic);
		    user.setChatId(chatId);
		    //lưu trữ user với chatId mới
		    userRepo.save(user);
		    //lưu lại chatTopic vừa khởi tạo
		    return chatTopicRepo.save(chatTopic);
		}
		return oldChatTopic;
	}
	
	//lấy tất cả các tin nhắn của message thoong qua nameTOpic
	public List<ChatMessages> getMessageInTopic(String nameTopic){
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		return chatTopic.getListChat();
	}
	
	//lấy tất cả các user cỉa topic 
	public Set<UserStatus> getUserInTopic(String nameTopic){
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		return chatTopic.getMembers();
	}
	
	// them mới một user vào topic 
	public ChatTopic addUserInTopic(String nameTopic ,String userId) {
		//lấy chatTopic thông qua nameTopic và xét lại giống như tạo topic nhưng khác Role
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		User user = userRepo.findByUserName(userId);
		UserStatus userStatus = new UserStatus();
		userStatus.setUser(user);
		userStatus.setRole(Role.USER);
		userStatus.setStatus(Status.JOIN);
		Set<UserStatus> member = chatTopic.getMembers();
		member.add(userStatus);
		chatTopic.setMembers(member);
		Set<String> chatId = user.getChatId();
	    chatId.add(nameTopic);
	    user.setChatId(chatId);
	    //lưu trữ user với chatId mới
	    userRepo.save(user);
	    // lưu trữ chatTopic mới
		return chatTopicRepo.save(chatTopic);
		
	}
	
	//lưu lại tất cả các tin nhắn trong đoạn chat 
	public ChatMessages saveChatMessageInTopic(ChatMessages chatmessage,String nameTopic) {
		ChatTopic chatTopic = chatTopicRepo.findOneByNameTopic(nameTopic);
		//lấy đoạn chat trước đó của topic
		List<ChatMessages> listChat = chatTopicRepo.findAllListChatByNameTopic(nameTopic);
		//them đoạn chat vừa được gửi và lưu trữ
		listChat.add(chatmessage);
		chatTopic.setListChat(listChat);
		chatTopicRepo.save(chatTopic);
		return chatmessage;	
	}
	
//	public List<ChatTopic> getAllGroupByUser(String userId) {
//		User user = userRepo.findByNickName(userId);
//		List<UserStatus> userStatus = userStatusRepo.findByUser(user);
//		List<ChatTopic> listChat = new ArrayList<>();
//		for(UserStatus u : userStatus) {
//		   ChatTopic chatTopic = chatTopicRepo.findByUserStatus(u);	
//		   listChat.add(chatTopic);
//		}
//		return listChat;
//	}
}
