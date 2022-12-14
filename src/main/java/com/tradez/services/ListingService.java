package com.tradez.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tradez.dao.CategoryRepo;
import com.tradez.dao.ListingRepo;
import com.tradez.models.Category;
import com.tradez.models.Listing;

@Service
public class ListingService {
	
	public static List<String> deliveries = Arrays.asList("Yes","No");
	public static List<String> conditions = Arrays.asList("New","Like New","Good","Fair");
	public static List<String> status = Arrays.asList("Available","Traded");

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ListingRepo listingRepo;
	
	public List<String> getDeliveries(){
		return deliveries;
	}
	public List<String> getStatus(){
		return status;
	}
	
	public List<String> getConditions(){
		return conditions;
	}
	
	public List<Category> getCategories(){
		return this.categoryRepo.findAll();
	}

	public void save(Listing listing, MultipartFile file) throws IOException {
		listing.setImg(file.getBytes());
		this.listingRepo.save(listing);
	}
	
	public void update(Listing listing, MultipartFile file) throws IOException {
		Listing old = this.listingRepo.findById(listing.getId()).get();
		
		if(!file.isEmpty()) {
			listing.setImg(file.getBytes());
		}
		else
		  listing.setImg(old.getImg());
		listing.setCreateDate(old.getCreateDate());
		listing.setCreatedBy(old.getCreatedBy());
		this.listingRepo.save(listing);
	}
	
	//finds by Authenticated User
	public List<Listing> findByUsername(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.listingRepo.findByCreatedBy(auth.getName());
	}
	//finds by any user name
	public Object findByUsername(String username) {
		return this.listingRepo.findByCreatedBy(username);
	}
	public List<Listing>getByCreator(String user){
		
		return this.listingRepo.findByCreatedBy(user);
	}
	
	public Listing findById(Long id) {
		return this.listingRepo.findById(id).get();
	}
	
	public byte[] findImageById(Long id) {
		Listing listing = this.listingRepo.findById(id).get();
		return listing.getImg();
	}
	
	public void deleteById(Long id) {
		this.listingRepo.deleteById(id);
	}

	public Object findAll() {
		// how to limit this?
		return this.listingRepo.findAll();
	}

	//for index page
	public List<Listing> getMostRecent(int resultsLimit){
		Pageable page = PageRequest.of(0, resultsLimit, Sort.by("createDate").descending());
//		return this.listingRepo.findAll(page).getContent();
		return this.listingRepo.findAllByStatus("available", page);
	}

	//for explore page
	public List<Listing> search(String search){
		List<Listing> resultList = null;
		
		Pageable page = PageRequest.of(0, 12, Sort.by("createDate").descending());
		
		if(search == null || search.isEmpty()) {
//			resultList = this.listingRepo.findAll(page).getContent();
			return this.listingRepo.findAllByStatus("available", page);
		}else {
			resultList = this.listingRepo.findAllByTitleLikeAndStatus("%" + search + "%", "available", page);
		}
		
		return resultList;
	}
	
	//for public profile page
	public List<Listing> findByCreatedByAndStatus(String username, String status, int resultsLimit){
		Pageable page = PageRequest.of(0, resultsLimit , Sort.by("createDate").descending());
		return this.listingRepo.findByCreatedByAndStatus(username, status, page);
	}
	
}
