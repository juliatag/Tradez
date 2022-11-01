package com.tradez.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tradez.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	public User findByUsername(String username);

}
