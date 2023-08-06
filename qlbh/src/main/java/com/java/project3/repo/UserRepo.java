package com.java.project3.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.project3.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query("SELECT u FROM User u WHERE u.name LIKE :x ")
	Page<User> searchByName(@Param("x") String s, Pageable pageable);
	
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.birthdate = :x")
	List<User> searchByBirthDate(@Param("x") Date s);
	
	
}
