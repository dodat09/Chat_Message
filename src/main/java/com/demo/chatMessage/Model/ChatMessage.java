package com.demo.chatMessage.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatmessage")
public class ChatMessage {
	
	@Id
	private String id;
	private String chatId;
	private String senderId;
	private String recipientId;
	private String content;
	private Date timeChat;
	public ChatMessage() {
		
	}
	public ChatMessage(String id, String chatId, String senderId, String recipientId, String content, Date timeChat) {
		super();
		this.id = id;
		this.chatId = chatId;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.content = content;
		this.timeChat = timeChat;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTimeChat() {
		return timeChat;
	}
	public void setTimeChat(Date timeChat) {
		this.timeChat = timeChat;
	}
	
	
	
	

}
