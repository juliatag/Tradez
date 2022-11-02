package com.tradez.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tradez.login.CustomUserDetails;
import com.tradez.models.User;
import com.tradez.services.UserService;

@Controller
public class PagesController {

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String homePage(Model model) {
		User user = getCurrentUser();
		model.addAttribute("user", user);
		return "index";
	}

	@RequestMapping("/explore")
	public String searchPage(@RequestParam(name = "search", required = false) String search, Model model) {
		User user = getCurrentUser();

		// FUTURE LOGIC
//		List<Listing> listings = listingService.getAll();
//		if(listings.length() > 0) {
//			model.addAttribute("listings",listings);
//		}

		model.addAttribute("user", user);
		model.addAttribute("search", search);
		return "explore";
	}

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {// replace with authenticated
																						// user
			String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();
			return userService.get(username);
		}
		return new User();
	}
}