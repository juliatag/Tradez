package com.tradez.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tradez.models.User;
import com.tradez.services.ListingService;
import com.tradez.services.UserService;
import com.tradez.validation.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private ListingService listingService;

	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.addValidators(new UserValidator());
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("authUser", this.service.getAuthUser());
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

	@RequestMapping("/login")
	public String Login(Model model) {
		return "login";
	}

	@GetMapping("/profile/{username}")
	public String getUserProfile(@PathVariable String username, Model model) {
		model.addAttribute("user", this.service.getByUsername(username));
		model.addAttribute("authUser", this.service.getAuthUser());
		return "profile";
	}

	@GetMapping("/dashboard/profile/edit")
	public String editProfilePage(Model model) {
		model.addAttribute("authUser", this.service.getAuthUser());
		return "profile_edit";
	}

	@PostMapping("/dashboard/profile/edit")
	public String update(User authUser, Model model) {
		authUser.setId(this.service.getAuthUser().getId());
		model.addAttribute("authUser", authUser);
		service.save(authUser);
		return "redirect:/dashboard";
	}

	@RequestMapping("/dashboard")
	public String dashboardPage(HttpServletRequest request, Model model) {
		model.addAttribute("authUser", this.service.getAuthUser());
		model.addAttribute("listings", this.listingService.findByUsername());
		return "dashboard";
	}

}
