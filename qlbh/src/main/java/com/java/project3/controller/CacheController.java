package com.java.project3.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {
	@Autowired
	CacheManager cacheManager;
	
	@GetMapping("/")
	public List<String> getCache() {
		List<String> cacheNames = cacheManager.getCacheNames().
				stream().collect(Collectors.toList());
		return cacheNames;
	}
	
	
}
