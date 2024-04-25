package com.demo.chatMessage.Model;

public class ChatMotification {

	private String id;
	private String senderId;
	private String recipientId;
	private String content;
	public ChatMotification() {
	
	}
	public ChatMotification(String id, String senderId, String recipientId, String content) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
}
