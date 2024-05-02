package com.demo.chatMessage.Model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
  @Id
  private String nickName;
  private String fullName;
  private Set<String> chatId;
  private Set<User> frientId;
  private Status status;
  
  public enum Status {
	  ONLINE ,
	  OFFLINE
  }

public User() {
	
}

public User(String nickName, String fullName, Set<String> chatId, Set<User> frientId, Status status) {
	super();
	this.nickName = nickName;
	this.fullName = fullName;
	this.chatId = chatId;
	this.frientId = frientId;
	this.status = status;
}

public String getNickName() {
	return nickName;
}

public void setNickName(String nickName) {
	this.nickName = nickName;
}

public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

public Set<String> getChatId() {
	return chatId;
}

public void setChatId(Set<String> chatId) {
	this.chatId = chatId;
}

public Set<User> getFrientId() {
	return frientId;
}

public void setFrientId(Set<User> frientId) {
	this.frientId = frientId;
}

public Status getStatus() {
	return status;
}

public void setStatus(Status status) {
	this.status = status;
}



  
}
