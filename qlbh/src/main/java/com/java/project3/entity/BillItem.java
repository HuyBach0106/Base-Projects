package com.java.project3.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class BillItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Bill bill;
	
	@ManyToOne
	private Product product; // dùng kiểu đối tượng để check NULL
	
//	thêm 2 cột này nên phải tạo bảng trung gian đó :(	
	private Integer quantity; 
	private Double price;
	
}
