//package com.java.project3.entity;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//
//import com.java.project3.entity.User;
//
//import lombok.Data;
//
//
//@Data
//public class UserRole {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	
//	@ManyToOne // many user role - to - one user
//	private User user;
//	
//	private String role; // ADMIN, MEMBER
//}
