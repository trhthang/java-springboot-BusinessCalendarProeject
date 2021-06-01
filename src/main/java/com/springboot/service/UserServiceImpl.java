package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.User;

import com.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public void saveUser(User user) {
		this.userRepository.save(user);
	}
}
