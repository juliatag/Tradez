package com.tradez.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tradez.models.User;
import com.tradez.services.UserService;

@Controller
public class PagesController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String homePage(Model model) {
		User user = new User();
		if(userService.get(1L) != null) {//replace with authenticated user
			user = userService.get(1L);
		}
		model.addAttribute("user",user);
		return "index";	
	}
	
	
	@RequestMapping("/explore")
	public String searchPage(@RequestParam (name = "search", required = false) String search, Model model) {
		User user = new User();
		if(userService.get(1L) != null) {//replace with authenticated user
			user = userService.get(1L);
		}
		
		//FUTURE LOGIC
//		List<Listing> listings = listingService.getAll();
//		if(listings.length() > 0) {
//			model.addAttribute("listings",listings);
//		}
		
		model.addAttribute("user",user);
		model.addAttribute("search",search);
		return "explore";	
	}
}
