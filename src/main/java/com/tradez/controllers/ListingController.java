package com.tradez.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		setupModel(model,"Add Listing",new Listing());
		return "listing_form";
	}
	
	@GetMapping("/updateListing/{id}")
	public String updateListingPage(@PathVariable("id") Long id,Model model) {
		setupModel(model,"Edit Listing",this.listingService.findById(id));
		return "listing_form";
	}
	
	@PostMapping("/saveListing")
	public String saveListing(Listing listing, @RequestPart MultipartFile imgFile) throws IOException {
		if(listing.getId() == null)
		  this.listingService.save(listing, imgFile);
		else
		  this.listingService.update(listing, imgFile);	
		return "redirect:/dashboard";
	}
	
//	*******ZACS DELETE CONFIRM*****
	
	@GetMapping("/confirmDelete/{id}")
	public String confirmDelete(@PathVariable Long id, Model model) throws IOException {
		model.addAttribute("listing", this.listingService.findById(id));
		System.out.print("confirm delete"+ id);
		return "confirm_delete";
	}
	@GetMapping("/deleteListing/{id}")
	public String deleteListing(@PathVariable Long id) throws IOException {
		System.out.print("deleting..."+ id);
		this.listingService.deleteById(id);
		return "redirect:/dashboard";
	}

	@GetMapping(value="/loadListingImage/{id}",produces = MediaType.ALL_VALUE)
	@ResponseBody
	public byte[] loadImageById(@PathVariable("id") Long id) throws IOException {
		return this.listingService.findImageById(id);
	}	
	
	private void setupModel(Model model,String title,Listing listing) {
		model.addAttribute("listing", listing);
		model.addAttribute("title", title);
		
		model.addAttribute("categories", this.listingService.getCategories());
		model.addAttribute("conditions", this.listingService.getConditions());
		model.addAttribute("deliveries", this.listingService.getDeliveries());
	}
}