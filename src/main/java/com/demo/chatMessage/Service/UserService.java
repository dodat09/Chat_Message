package com.demo.chatMessage.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Model.User.Status;
import com.demo.chatMessage.Repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	public void saveUser(User user) {
		user.setStatus(Status.ONLINE);
		userRepo.save(user);
	}
	
	public void disconect(User user) {
		var storeUser = userRepo.findById(user.getNickName()).orElse(null);
		if(storeUser != null) {
			storeUser.setStatus(Status.OFFLINE);
			userRepo.save(storeUser);
		}
	}
	public List<User> findConnectedUsers(){
		return userRepo.findAllByStatus(Status.ONLINE);
	}
	
	public User getUserById(String userId) {
		return userRepo.findByNickName(userId);
	}
	
}
