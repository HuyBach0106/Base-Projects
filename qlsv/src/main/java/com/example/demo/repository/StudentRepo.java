package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Integer>{
	@Query("SELECT st.id FROM Student st")
	List<Integer> findAllId();
	
	@Query("SELECT s FROM Student s")
	List<Student> getAll();
	
//	Page<Student> findByIdIn(List<Integer> ids,  Pageable pageable);
	
	@Query("SELECT s FROM Student s JOIN s.user u WHERE u.name LIKE :x")
	Page<Student> searchStudentByName(@Param("x") String name, Pageable pageable);
	
	@Query("SELECT s FROM Student s JOIN s.user u WHERE s.studentCode LIKE :code AND u.name LIKE :name")
	Page<Student> searchByNameAndCode(@Param("code") String code, @Param("name") String name, Pageable pageable);
	/////
	@Query("SELECT s FROM Student s WHERE s.id = :x")
	Student getStudentById(@Param("x") Integer id);
	
	@Query("SELECT s FROM Student s join s.user u WHERE s.studentCode LIKE :x ")
    Page<Student> searchByStudentCode(@Param("x") String s, Pageable pageable);
}
