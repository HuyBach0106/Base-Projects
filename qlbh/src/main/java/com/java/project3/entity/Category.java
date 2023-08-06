package com.java.project3.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Data
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// javax validation
	@NotBlank(message = "{not.empty}")
	@Size(min = 6, max = 30)
	private String name;
	
	@CreatedDate //tu gen Date
	private Date createdAt;
}
