package com.tradez.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tradez.login.CustomUserDetails;
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
		//add verification logic
		user.setPassword(encodePassword(user.getPassword()));
		service.save(user);
		
		model.addAttribute("user", user);
		return "signup_confirmation";	
	}
	
	@GetMapping("/profile/{username}")
	public String getUser(@PathVariable String username, Model model) {
		User user = service.getByUsername(username);
		model.addAttribute("user", user);
		return "profile";
		
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		User user = getCurrentUser();
		model.addAttribute("user", user);
		return "dashboard";	
	}
	
	@RequestMapping("/dashboard/profile/edit")
	public String editProfilePage(Model model) {
		User user = getCurrentUser();
		model.addAttribute("user", user);
		return "edit_profile";	
	}
	
	@PostMapping("/dashboard/profile/edit")
	public String update(Model model) {
		User user = getCurrentUser();
		//add verification logic
		service.save(user);
		
		model.addAttribute("user", user);
		return "redirect:/dashboard";	
	}
	
	
	
	public String encodePassword(String password) {
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {//replace with authenticated user
			String username  = ((CustomUserDetails) auth.getPrincipal()).getUsername();
			return service.get(username);
		}
		return new User();
	}

}
