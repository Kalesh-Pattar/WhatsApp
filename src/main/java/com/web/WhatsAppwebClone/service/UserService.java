package com.web.WhatsAppwebClone.service;

import java.util.List;

import com.web.WhatsAppwebClone.dto.UserDto;
import com.web.WhatsAppwebClone.exception.UserException;
import com.web.WhatsAppwebClone.modal.User;
import com.web.WhatsAppwebClone.request.UpdateUserRequest;

public interface UserService {
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public User findUserById(Integer userId) throws UserException;
	
	public List<User> searchUser(String query);
}
