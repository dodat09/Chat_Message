package com.demo.chatMessage.Model;

import java.util.Date;

public class ChatMessages {
   private String userId;
   private String content;
   private Date timestamp;
public ChatMessages() {
	
}
public ChatMessages(String userId, String content, Date timestamp) {
	super();
	this.userId = userId;
	this.content = content;
	this.timestamp = timestamp;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Date getTimestamp() {
	return timestamp;
}
public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
}
   
   
}
