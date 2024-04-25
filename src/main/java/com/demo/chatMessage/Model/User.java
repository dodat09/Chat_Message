package com.demo.chatMessage.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
  @Id
  private String nickName;
  private String fullName;
  private Status status;
  
  public enum Status {
	  ONLINE ,
	  OFFLINE
  }

public User() {
	
}

public User(String nickName, String fullName, Status status) {
	super();
	this.nickName = nickName;
	this.fullName = fullName;
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

public Status getStatus() {
	return status;
}

public void setStatus(Status status) {
	this.status = status;
}

  
}
