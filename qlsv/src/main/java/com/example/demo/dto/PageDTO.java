package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {
	private int totalPages;
	private long totalElements;
	private List<T> contents;
}
