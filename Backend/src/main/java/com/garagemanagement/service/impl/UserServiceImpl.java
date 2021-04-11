package com.garagemanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagemanagement.entity.User;
import com.garagemanagement.repository.UserRepository;

@Service
public class UserServiceImpl{
	
	@Autowired
	private UserRepository userRepository;
	public User loadUserByUsernameAndPassword(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, password);
		return user;
	}
}