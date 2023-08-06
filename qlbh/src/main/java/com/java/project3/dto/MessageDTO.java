package com.java.project3.dto;

import lombok.Data;

@Data
public class MessageDTO {
	private int id;
	private String to;
	private String toName;
	private String subject;
	private String content;
	
	private boolean status;
	
}
