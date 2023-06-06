package com.web.WhatsAppwebClone.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.web.WhatsAppwebClone.exception.ChatException;
import com.web.WhatsAppwebClone.exception.UserException;
import com.web.WhatsAppwebClone.modal.Chat;
import com.web.WhatsAppwebClone.modal.Message;
import com.web.WhatsAppwebClone.modal.User;
import com.web.WhatsAppwebClone.request.SendMessageRequest;
import com.web.WhatsAppwebClone.service.ChatService;
import com.web.WhatsAppwebClone.service.MessageService;
import com.web.WhatsAppwebClone.service.UserService;

@RestController
public class RealTimeChat {
	
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private ChatService chatService;
	
    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message){
    	
    	System.out.println("receive message in public ---------- ");
    	
    	simpMessagingTemplate.convertAndSend("/group/"+message.getChat().getId().toString(), message);
    	
        return message;
    }
    
	@MessageMapping("/chat/{groupId}")
	public Message sendToUser(@Payload SendMessageRequest req, @Header("Authorization") String jwt,@DestinationVariable String groupId) throws UserException, ChatException {	
		System.out.println("recived private message - - - - - "+req);
		User user=userService.findUserProfile(jwt);
		System.out.println("userr private message - - - - - - "+user);
		req.setUserId(user.getId());
		
		Chat chat=chatService.findChatById(req.getChatId());
		
		Message createdMessage = messageService.sendMessage(req);
		
		User reciverUser=reciver(chat, user);
		
		simpMessagingTemplate.convertAndSendToUser(groupId, "/private", createdMessage);
		
		return createdMessage;
	}
	
	public User reciver(Chat chat,User reqUser) {
		Iterator<User> iterator = chat.getUsers().iterator();

		User user1 = iterator.next(); // get the first user
		User user2 = iterator.next();
		
		if(user1.getId().equals(reqUser.getId())){
			return user2;
		}
		return user1;
	}
	
	

}
