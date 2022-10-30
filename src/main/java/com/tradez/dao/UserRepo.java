package com.tradez.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.tradez.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u Where u.email=?1")
	public User findByEmail(String email);

}
