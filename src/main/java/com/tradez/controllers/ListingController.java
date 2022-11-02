package com.tradez.controllers;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tradez.models.Listing;
import com.tradez.services.ListingService;

@Controller
public class ListingController {

	@Autowired
	ListingService listingService;

	@GetMapping("/createListing")
	public String createListingPage(Model model) {
		model.addAttribute("listing", new Listing());
		setupModel(model);
		return "new_listing";
	}
	
	@PostMapping("/createListing")
	public String createListing(Listing listing, @RequestPart MultipartFile imgFile) throws IOException {
		this.listingService.save(listing, imgFile);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/updateListing/{id}")
	public String updateListingPage(@PathVariable("id") Long id,Model model) {
		model.addAttribute("listing", this.listingService.findById(id));
		setupModel(model);
		return "update_listing";
	}
	
	@PostMapping("/updateListing")
	public String updateListing(Listing listing, @RequestPart MultipartFile imgFile) throws IOException {
		this.listingService.update(listing, imgFile);
		return "redirect:/dashboard";
	}
	
	
	@GetMapping("/deleteListing/{id}")
	public String deleteListing(@PathVariable Long id) throws IOException {
		this.listingService.deleteById(id);
		return "redirect:/dashboard";
	}

	@RequestMapping("/dashboard")
	public String dashboardPage(HttpServletRequest request, Model model) {

		model.addAttribute("listings", this.listingService.findByUsername());
		return "dashboard";
	}
	
	@GetMapping(value="/loadListingImage/{id}",produces = MediaType.ALL_VALUE)
	@ResponseBody
	public byte[] loadImageById(@PathVariable("id") Long id) throws IOException {
		return this.listingService.findImageById(id);
	}	
	
	
	private void setupModel(Model model) {
		model.addAttribute("categories", this.listingService.getCategories());
		model.addAttribute("conditions", this.listingService.getConditions());
		model.addAttribute("deliveries", this.listingService.getDeliveries());
	}
}
