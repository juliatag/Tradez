package com.tradez.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradez.models.Listing;

@Repository
public interface ListingRepo extends JpaRepository<Listing, Long> {
	
	
	List<Listing> findByCreatedBy(String username);
	List<Listing> findFirst10ByTitleLike(String search);
	List<Listing> findByTitleLike(String search);
	
	//refining by status
	List<Listing> findByCreatedByAndStatus(String username, String status, Pageable Page);
	List<Listing> findAllByStatus(String status, Pageable Page);
	List<Listing> findAllByTitleLikeAndStatus(String search, String status, Pageable Page);
	
}
