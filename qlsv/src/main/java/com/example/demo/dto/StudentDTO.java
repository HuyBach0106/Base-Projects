package com.example.demo.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
	private Integer id;
	
	@NotBlank
	private String studentCode;
	
	private Integer userId;
	
	private String userName;
}
