package com.java.project3.dto;

import java.util.Date;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

// GRAPQL
@Data
public class BillItemDTO {
	private Integer id;
	
	@JsonBackReference
	private BillDTO billDTO; // thuộc về 1 bill hoặc 1 product
	
	private ProductDTO product;
	@Min(0)
	private Integer quantity;
	@Min(0)
	private Double price;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date buyDate;
}
