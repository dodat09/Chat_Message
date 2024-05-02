package com.demo.chatMessage.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.ChatRoom;
import com.demo.chatMessage.Model.ChatTopic;
import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Model.User.Status;
import com.demo.chatMessage.Repository.ChatRoomRepo;
import com.demo.chatMessage.Repository.ChatTopicRepo;
import com.demo.chatMessage.Repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ChatRoomRepo chatRoomRepo;
	
	@Autowired
	private ChatTopicRepo chatTopicRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	//lưu user
	public void saveUser(User user) {
		Set<User> listFriend = new HashSet();
		Set<String> listChatId = new HashSet();
		user.setChatId(listChatId);
		user.setFrientId(listFriend);
		user.setStatus(Status.ONLINE);
		userRepo.save(user);
	}
	//ngắt kết nối user bằng Status.Off
	public void disconect(User user) {
		var storeUser = userRepo.findById(user.getNickName()).orElse(null);
		if(storeUser != null) {
			storeUser.setStatus(Status.OFFLINE);
			userRepo.save(storeUser);
		}
	}
	//lấy tất cả các user kết nối 
	// trước hết phải tim được user đăng nhập bằng Security
	// rồi lấy ra tất cả các chatId đã lưu
	public List<Object> findConnectedUsers(String nickName){
		
		User user = userRepo.findByNickName(nickName);
		// tạo một list các connected có thể gồm có user và group
		List<Object> listConnected = new ArrayList<>();
		//lấy SetId từ User đang đăng nhập
		Set<String> listChatId = user.getChatId();
		//duyệt set ChatId
		try {
			if(listChatId!=null) {
				for(String chatId:listChatId) {
			//kiểm tra nếu ChatRoom ko tồn tại thì nó sẽ là group
			if(chatRoomRepo.findByChatId(chatId) ==null) {
				// lấy chatTopic dựa trên chatId
				ChatTopic chatTopic =chatTopicRepo.findOneByNameTopic(chatId);
				listConnected.add(chatTopic);
			}else {
				//lấy chatRoom từ chatId và từ đó lấy được userRecipient rồi thêm vào listConnected
				ChatRoom chatRoom = chatRoomRepo.findByChatId(chatId);
				User userRecipient = userRepo.findByNickName(chatRoom.getRecipientId());
				listConnected.add(userRecipient);
				
			}
		}
		return listConnected;
			}
		}catch(NullPointerException e) {
			
		}
		return listConnected;
	}
	
	public User getUserById(String userId) {
		return userRepo.findByNickName(userId);
	}
	
	public User addFriend(String userId,String friendId) {
		User add = userRepo.findByNickName(userId);
		User friend = userRepo.findByNickName(friendId);
		if(add!=null&&friend!=null) {
			Set<User> listfriendInAdd = add.getFrientId();
		    Set<User> listfriendInFriend = friend.getFrientId();
		
			if(listfriendInAdd!=null) {
				for(User u: listfriendInAdd) {
		          	if(u == friend) {
				     logger.info("Hai bạn đã là bạn bè của nhau");
			       }else {
				   listfriendInAdd.add(friend);
				   listfriendInFriend.add(add);
				   friend.setFrientId(listfriendInFriend);
				   add.setFrientId(listfriendInAdd);
				   userRepo.save(add);
				   userRepo.save(friend);
				   logger.info("Bạn và "+friend.getNickName()+"đã là bạn bè của nhau từ ngày hôm nay!");
			      }
		       }
	        }else {		     
	        	  Set<User> listTmp = new HashSet();
	        	  listTmp.add(friend);
	        	  if(listfriendInFriend!=null) {
	        		  listfriendInFriend.add(add);
	        		  friend.setFrientId(listfriendInFriend);
	        		  userRepo.save(friend);
	        	  }else {
	        		  Set<User> listTmpF = new HashSet();
	        		  listTmpF.add(add);
	        		  friend.setFrientId(listTmpF);;
	        		  userRepo.save(friend);
	        	  }
	        	  add.setFrientId(listTmp);
	        	  userRepo.save(add); 
	        	 
	          }
		}else {
			logger.error("Khong tim thay user hoặc friend dựa tren ID");
		}
		return friend;
	}
	
	
}
