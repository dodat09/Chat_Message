package com.demo.chatMessage.Service;

import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.ChatRoom;
import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Repository.ChatRoomRepo;
import com.demo.chatMessage.Repository.UserRepo;

@Service
public class ChatRoomService {

	
    @Autowired
    private ChatRoomRepo chatRoomRepo;
    
    @Autowired
    private UserRepo userRepo;
	
	
//	public Optional<String> getIdChat(String senderId,String recipientId,boolean createNewRoomIfNotExits) {	 
//		return chatRoomRepo.findBySenderIdAndRecipientId(senderId,recipientId)
//				.map(ChatRoom::getChatId)
//				.or(()->{
//					if(createNewRoomIfNotExits) {
//						String chatId = createChatId(senderId,recipientId);
//						return Optional.of(chatId);
//					}else {
//						return Optional.empty();
//					}
//				});
//	}
	public String getIdChat(String senderId,String recipientId,boolean createNewRoomIfNotExits) {
		// lây chatRoom thông qua senderid và recipientId
		 ChatRoom chatRoom = chatRoomRepo.findBySenderIdAndRecipientId(senderId, recipientId);
		 if(chatRoom ==null) {
			String chatId = createChatId(senderId,recipientId);
			return chatId;
		 }else {
			 return chatRoom.getChatId();
		 }
	}
	 
	
	private String createChatId(String senderId,String recipientId) {
//		String chatId = String.format("%s_%s", senderId,recipientId);
//		ChatRoom senderRecipient = new ChatRoom();
//		   senderRecipient.setChatIdd(chatId);
//		   senderRecipient.setSenderId(senderId);
//		   senderRecipient.setRecipientId(recipientId);
//		ChatRoom recipientSender = new ChatRoom();
//		recipientSender.setChatIdd(chatId);
//		recipientSender.setSenderId(recipientId);
//		recipientSender.setRecipientId(senderId);
//		
//		chatRoomRepo.save(senderRecipient);
//		chatRoomRepo.save(recipientSender);
		Random random = new Random();
		int randomId = random.nextInt(900000)+100000;
		String chatId = String.valueOf(randomId);
		//lấy user từ senderId
		User user = userRepo.findByUserName(senderId);
		User friend = userRepo.findByUserName(recipientId);
		//cấu hình lại các thông tin như chatId giữa user và lưu danh sách bạn bè
		Set<String> listChatIdSender = user.getChatId();
		listChatIdSender.add(chatId);
		user.setChatId(listChatIdSender);
		Set<User> listFriendSender = user.getFrientId();
		listFriendSender.add(friend);
		user.setFrientId(listFriendSender);
		userRepo.save(user);
		//tương tự vơi friend
		Set<String> listChatIdRecipient = user.getChatId();
		listChatIdRecipient.add(chatId);
		user.setChatId(listChatIdSender);
		Set<User> listFriendRecipient = user.getFrientId();
		listFriendRecipient.add(friend);
		friend.setChatId(listChatIdRecipient);
		friend.setFrientId(listFriendRecipient);
		userRepo.save(friend);
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChatIdd(chatId);
		chatRoom.setRecipientId(recipientId);
		chatRoom.setSenderId(senderId);
		chatRoomRepo.save(chatRoom);
		return chatId;
		
	}
	
}
