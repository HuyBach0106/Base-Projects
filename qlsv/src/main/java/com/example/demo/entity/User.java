package com.example.demo.entity;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
//import com.example.demo.entity.UserRole;

import lombok.Data;
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String name;
	
	private String avatar;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	private String email;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;
	
	@JsonIgnore
	@Transient
	private MultipartFile file;
	
	@CreatedDate
	private Date createdAt;
	
	@LastModifiedDate
	private Date lastUpdateAt;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @ManyToMany(mappedBy="users", fetch=FetchType.LAZY)
    List<Group> groups;
}
