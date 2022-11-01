package com.tradez.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tradez.models.Listing;
import com.tradez.models.User;
import com.tradez.services.ListingService;

@Controller
public class ListingController {
	@Autowired
	private ListingService service;
	
	@GetMapping("/listing")
	public String listingPage(Model model) {
		Listing listing = new Listing();
		model.addAttribute("listing", listing);
		return "listing";	
	}
	
	@PostMapping("/listing")
	public String list(Listing listing, Model model) {
		
		User user = new User();
		
		//add verification logic
		service.save(listing);
		
		model.addAttribute("user", user);
		return "index";	
	}

}
