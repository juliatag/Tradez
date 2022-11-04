package com.tradez.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tradez.models.User;
import com.tradez.services.ListingService;
import com.tradez.services.UserService;

@Controller
public class PagesController {
	
	@Autowired
	UserService userService;
	@Autowired
	private ListingService listingService;
	
	@RequestMapping("/")
	public String homePage(Model model) {
		model.addAttribute("listings", this.listingService.getMostRecent(6));
		return "index";	
	}
	
	
	@RequestMapping("/explore")
	public String searchPage(@RequestParam (name = "search", required = false) String search, Model model) {
		model.addAttribute("listings", this.listingService.search(search));
		model.addAttribute("search",search);
		return "explore";	
	}
}
