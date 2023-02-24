package com.example.demo.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
	private Integer id;
	
	@NotBlank
	@Size(min = 6, max = 30)
	private String name;
	
	private List<Integer> userIds;
}
