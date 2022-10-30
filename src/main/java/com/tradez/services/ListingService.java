package com.tradez.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradez.dao.ListingRepo;
import com.tradez.models.Listing;


@Service
public class ListingService {
	
	@Autowired
	private ListingRepo repo;
	
	public void save(Listing listing) {
		repo.save(listing);
	}
	
	public Listing get(Long id) {
		return repo.findById(id).get();
	}


}
