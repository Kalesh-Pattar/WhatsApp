package com.web.WhatsAppwebClone.service;

import java.util.List;

import com.web.WhatsAppwebClone.exception.ChatException;
import com.web.WhatsAppwebClone.exception.MessageException;
import com.web.WhatsAppwebClone.exception.UserException;
import com.web.WhatsAppwebClone.modal.Message;
import com.web.WhatsAppwebClone.request.SendMessageRequest;

public interface MessageService  {
	
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages(Integer chatId) throws ChatException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public String deleteMessage(Integer messageId) throws MessageException;

}
