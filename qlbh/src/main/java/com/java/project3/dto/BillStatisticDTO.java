package com.java.project3.dto;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillStatisticDTO {
	// bên SQL trả 2 thuộc tính nên ở đây cần 2 thuộc tính thôi
	private long quantity;
	private String time;
}
