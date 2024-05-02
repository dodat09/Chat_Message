package com.demo.chatMessage.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="userstatus")
public class UserStatus {
   @Id
   private String id;
   private User user;
   private Status status;
   private Role role;
   
   public enum Role{
	   ADMIN , USER
   }
   
   public enum Status{
	   JOIN, LEAVE
   }

public UserStatus() {
}

public UserStatus(User user, Status status, Role role) {
	super();
	this.user = user;
	this.status = status;
	this.role = role;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Status getStatus() {
	return status;
}

public void setStatus(Status status) {
	this.status = status;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

   
}
