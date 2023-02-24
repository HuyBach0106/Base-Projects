package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
	private Integer id;
	
	private double score;
	
	private Integer studentId;
	
	private String studentCode;
	
	private Integer courseId;
	
	private String courseName;
}
