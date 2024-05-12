package com.demo.chatMessage.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Model.User.Status;

@Repository
public interface UserRepo extends MongoRepository<User,String>{
   List<User> findAllByStatus(Status status);
   
   User findByUserName(String username);
}
