package com.tradez.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradez.models.Category;

public interface CategoryRepo extends JpaRepository<Category,Long>{

}
