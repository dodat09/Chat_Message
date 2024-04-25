package com.demo.chatMessage.Model;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="chattopic")
public class ChatTopic {

	@Id
	private String nameTopic;
	private Set<UserStatus> members;
	private List<ChatMessages> listChat;	
	public ChatTopic() {
		
	}
	public ChatTopic(String nameTopic, Set<UserStatus> members, List<ChatMessages> listChat) {
		super();
		this.nameTopic = nameTopic;
		this.members = members;
		this.listChat = listChat;
	}
	public String getNameTopic() {
		return nameTopic;
	}
	public void setNameTopic(String nameTopic) {
		this.nameTopic = nameTopic;
	}
	public Set<UserStatus> getMembers() {
		return members;
	}
	public void setMembers(Set<UserStatus> members) {
		this.members = members;
	}
	public List<ChatMessages> getListChat() {
		return listChat;
	}
	public void setListChat(List<ChatMessages> listChat) {
		this.listChat = listChat;
	}
	
		

}
