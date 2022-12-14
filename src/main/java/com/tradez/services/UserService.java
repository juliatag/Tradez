package com.tradez.services;

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
	
	public void save(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
	}
	
	public User getById(Long id) {
		return repo.findById(id).get();
	}
	
	public User getByUsername(String username) {
		return this.repo.findByUsername(username);
	}
	
	public User getByEmail(String email) {
		return this.repo.findByEmail(email);
	}
	
	public User getByUsernameOrEmail(String usernameOrEmail) {
		if(repo.findByEmail(usernameOrEmail) == null) {
			return repo.findByUsername(usernameOrEmail);
		}
		return repo.findByEmail(usernameOrEmail);
	}
	
	public User getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.repo.findByUsername(auth.getName());
	}
	
	public String validateUsernameAndEmail(User user) {
		
		String email = user.getEmail();
		String username = user.getUsername();
		
		if(email.isEmpty() || username.isEmpty()) {
			System.out.println("email or username is empty");
			return null;
		}
		
		User dbUser = this.repo.findByEmail(email);
		if(dbUser != null) {
			return "email";
		}
		
		dbUser = this.repo.findByUsername(username);
		if(dbUser != null) {
			return "username";
		}
		return null;
	}

}
