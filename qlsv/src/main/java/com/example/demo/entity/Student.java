package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.demo.entity.Score;

import lombok.Data;

@Entity
@Data
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	@NotBlank
	private String studentCode;
	
	@OneToOne(cascade = CascadeType.ALL) //1-1
	@PrimaryKeyJoinColumn() // chỉ định khóa chính được sử dụng làm khóa ngoại cho thực thể mẹ
	private User user;
	
	@OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
	private List<Score> scores;
}
