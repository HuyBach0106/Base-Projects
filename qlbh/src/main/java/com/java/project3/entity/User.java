package com.java.project3.entity;

import java.util.Date; 
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String name;
	
	private String avatar; // URL
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	private String email;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;
	
	@JsonIgnore
	@Transient // Field is not persistent
	private MultipartFile file; // file có thể ko cần thiết cho entity
	
	@CreatedDate /// tu gen
	private Date createdAt;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<String> roles;      //roles[0] và roles[1]                  // dùng join column vì chỉ có 2 elements, từ 3 elements thì tạo
													   // bảng userRoles riêng như bên dưới
//	ADMIN - MEMBER
	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//	private List<UserRole> userRoles;
	
	

}
