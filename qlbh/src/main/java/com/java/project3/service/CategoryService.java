package com.java.project3.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.project3.dto.CategoryDTO;
import com.java.project3.dto.PageDTO;
import com.java.project3.dto.UserDTO;
import com.java.project3.entity.Category;
import com.java.project3.entity.User;
import com.java.project3.repo.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	
	@Transactional // tạo thêm bản ghi ới phải search lại tư đầu
	@CacheEvict(cacheNames = "category-search", allEntries = true)
	public void create(CategoryDTO categoryDTO) {
		Category category = new ModelMapper().map(categoryDTO, Category.class);
		categoryRepo.save(category);
		
		// trả về id sau khi tạo ( ví dụ Frontend cần Id để trả về chi tiết, etc, ..)
		categoryDTO.setId(category.getId());
	}
	
	@Transactional
	@Caching(evict = {
	@CacheEvict(cacheNames = "categories", key = "#id"),
	@CacheEvict(cacheNames = "category-search", allEntries = true) // Cache: update và delete giống nhau
	})
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);
		category.setName(categoryDTO.getName());
		categoryRepo.save(category);
	}
	
	@Transactional
	@Caching(evict = {
	@CacheEvict(cacheNames = "categories", key = "#id"),
	@CacheEvict(cacheNames = "category-search", allEntries = true)
	})
	public void delete(int id) {
		categoryRepo.deleteById(id);
	}
	
	
	@Cacheable(cacheNames = "category-search") // dữ liệu khác nhau phải để tên cache khác nhau
	public PageDTO<CategoryDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Category> pageRS = categoryRepo.searchByName("%" + name + "%", pageable);
		PageDTO<CategoryDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageDTO.getTotalElements());
		
		// java 8: lambda, stream
		List<CategoryDTO> categoryDTOs = pageRS.get().map(category -> new ModelMapper().map(category, CategoryDTO.class))
				.collect(Collectors.toList());
		pageDTO.setContents(categoryDTOs); // set vao pageDTO
		return pageDTO;
	}
	
	
	public CategoryDTO getById(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
		CategoryDTO categoryDTO = new ModelMapper().map(category, CategoryDTO.class);
		return categoryDTO;
	}
}
