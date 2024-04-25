package com.demo.chatMessage.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.ChatRoom;
import com.demo.chatMessage.Repository.ChatRoomRepo;

@Service
public class ChatRoomService {

	
    @Autowired
    private ChatRoomRepo chatRoomRepo;
	
	
	public Optional<String> getIdChat(String senderId,String recipientId,boolean createNewRoomIfNotExits) {	 
		return chatRoomRepo.findBySenderIdAndRecipientId(senderId,recipientId)
				.map(ChatRoom::getChatId)
				.or(()->{
					if(createNewRoomIfNotExits) {
						String chatId = createChatId(senderId,recipientId);
						return Optional.of(chatId);
					}else {
						return Optional.empty();
					}
				});
	}
	 
	
	private String createChatId(String senderId,String recipientId) {
		String chatId = String.format("%s_%s", senderId,recipientId);
		ChatRoom senderRecipient = new ChatRoom();
		   senderRecipient.setChatIdd(chatId);
		   senderRecipient.setSenderId(senderId);
		   senderRecipient.setRecipientId(recipientId);
		ChatRoom recipientSender = new ChatRoom();
		recipientSender.setChatIdd(chatId);
		recipientSender.setSenderId(recipientId);
		recipientSender.setRecipientId(senderId);
		
		chatRoomRepo.save(senderRecipient);
		chatRoomRepo.save(recipientSender);
		return chatId;
		
	}
	
}
