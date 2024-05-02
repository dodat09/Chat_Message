package com.demo.chatMessage.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Model.UserStatus;

@Repository
public interface UserStatusRepo extends MongoRepository<UserStatus,String>{
   List<UserStatus>  findByUser(User user);
}
