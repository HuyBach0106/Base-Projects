package com.java.project3.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class BillDTO {
	private Integer id;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date buyDate;
	private UserDTO user;
	
	@JsonManagedReference
	private List<BillItemDTO> billItems; // tên giống để dùng modelmapper
	
}
