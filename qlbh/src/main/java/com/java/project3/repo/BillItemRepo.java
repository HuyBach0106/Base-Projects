package com.java.project3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.project3.entity.BillItem;

public interface BillItemRepo extends JpaRepository<BillItem, Integer>{
	
}
