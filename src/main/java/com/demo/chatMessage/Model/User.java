package com.demo.chatMessage.Model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
  @Id
  private String id;
  
  private String userName;
  private String password;
  private Set<String> chatId;
  private Set<User> frientId;
  private Status status;
  private String role;
  
  public enum Status {
	  ONLINE ,
	  OFFLINE
  }

public User() {
	
}

public User(String id, String userName, String password, Set<String> chatId, Set<User> frientId, Status status,
		String role) {
	super();
	this.id = id;
	this.userName = userName;
	this.password = password;
	this.chatId = chatId;
	this.frientId = frientId;
	this.status = status;
	this.role = role;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
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

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}


}
