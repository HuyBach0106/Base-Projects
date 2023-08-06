package com.java.project3.dto;

import java.util.List;

import lombok.Data;


// kiểu generic
@Data
public class PageDTO<T> {
	private int totalPages;
	private long totalElements;
	private List<T> contents;
}
