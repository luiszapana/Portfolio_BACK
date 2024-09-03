package com.luis.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.security.entity.User;
import com.luis.security.entity.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private IUserRepository userRepository;
	
	public Optional<User> getByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
}
