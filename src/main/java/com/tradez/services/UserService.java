package com.tradez.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tradez.dao.UserRepo;
import com.tradez.models.User;

@Service
public class UserService{
	
	@Autowired
	private UserRepo repo;
	
	@Transactional
	public void save(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user = repo.save(user);
		
	}
	
	public User get(Long id) {
		return repo.findById(id).get();
	}
	
	public User findByUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.repo.findByEmail(auth.getName());
	}

}
