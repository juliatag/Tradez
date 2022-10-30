package com.tradez.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tradez.models.User;
import com.tradez.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup";	
	}
	
	@PostMapping("/signup")
	public String signUp(User user, Model model) {
		
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		//add verification logic
		service.save(user);
		
		model.addAttribute("user", user);
		return "signup_confirmation";	
	}
	
	@GetMapping("/profile/{username}")//change this for username once there is a getByUsername method
	public String getUser(@PathVariable Long id, Model model) {
		User user = service.get(id);//change this for username once there is a getByUsername method
		model.addAttribute("user", user);
		return "profile";
		
	}
	
	@RequestMapping("/editprofile/{id}")
	public String editProfilePage(@PathVariable Long id, Model model) {
		User user = service.get(id);//change for current logged in user
		model.addAttribute("user", user);
		return "edit_profile";	
	}
	
	@PostMapping("/editprofile")
	public String update(Model model) {
		User user = service.get(1L);//change for current logged in user
		//add verification logic
		service.save(user);
		
		model.addAttribute("user", user);
		return "dashboard";	
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		User user = service.get(1L);//change for current logged in user
		model.addAttribute("user", user);
		return "dashboard";	
	}

}
