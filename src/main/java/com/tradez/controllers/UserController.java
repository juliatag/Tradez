package com.tradez.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

		// add verification logic
		service.save(user);

		model.addAttribute("user", user);
		return "signup_confirmation";
	}

	@GetMapping("/profile") // change this for username once there is a getByUsername method
	public String getUser(Model model) {
		model.addAttribute("user", this.service.findByUsername());
		return "profile";

	}

	@RequestMapping("/editprofile")
	public String editProfilePage(Model model) {
		model.addAttribute("user", this.service.findByUsername());
		return "profile_form";
	}

	@PostMapping("/editprofile")
	public String update(Model model) {
		User user = service.get(1L);// change for current logged in user
		// add verification logic
		service.save(user);

		model.addAttribute("user", user);
		return "dashboard";
	}

}
