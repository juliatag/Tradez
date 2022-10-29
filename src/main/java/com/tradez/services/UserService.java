package com.tradez.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradez.dao.UserRepo;
import com.tradez.models.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	public void save(User user) {
		repo.save(user);
	}
	
	public User get(Long id) {
		return repo.findById(id).get();
	}

}
