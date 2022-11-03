package com.tradez.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradez.models.Listing;

@Repository
public interface ListingRepo extends JpaRepository<Listing, Long> {
	
	
	List<Listing> findByCreatedBy(String username);
	
}
