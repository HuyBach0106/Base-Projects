package com.java.project3.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.project3.entity.Category;


public interface CategoryRepo extends JpaRepository<Category,Integer>
{
	@Query("SELECT c FROM Category c WHERE c.name LIKE :x")
	Page<Category> searchByName(@Param("x") String s, Pageable pageable);
}
