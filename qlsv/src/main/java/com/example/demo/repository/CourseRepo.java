package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Course;

public interface CourseRepo extends JpaRepository<Course,Integer>{
	
	@Query("SELECT c FROM Course c WHERE c.name = :x")
	Page<Course> searchByName(@Param("x") String s, Pageable pageable);
}
