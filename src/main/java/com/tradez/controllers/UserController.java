package com.tradez.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tradez.models.User;
import com.tradez.services.UserService;
import com.tradez.validation.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.addValidators(new UserValidator());
	}

	@GetMapping("/login.html")
	public String login() {
		return "login";
	}

	// Login form with error
	@GetMapping("/login-error.html")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/signup")
	public String signUp(@Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "signup";
		}
		service.save(user);
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
