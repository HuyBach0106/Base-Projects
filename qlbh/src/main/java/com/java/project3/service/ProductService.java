package com.java.project3.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.project3.dto.PageDTO;
import com.java.project3.dto.ProductDTO;
import com.java.project3.entity.Category;
import com.java.project3.entity.Product;
import com.java.project3.repo.CategoryRepo;
import com.java.project3.repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Transactional
	public void create(ProductDTO productDTO) {
		Product product = new ModelMapper().map(productDTO, Product.class);
		Category category = categoryRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException::new);
		product.setCategory(category);
		productRepo.save(product);
		
		//tra ve id sau khi tao ( if needed )
		productDTO.setId(product.getId());
	}
	
	@Transactional
	public void update(ProductDTO productDTO) {
		Product product = productRepo.
		findById(productDTO.getId()).orElseThrow(NoResultException::new);
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setImage(productDTO.getImage());
		product.setDescription(productDTO.getDescripton());
		product.setCreatedAt(productDTO.getCreatedAt());
		Category category = categoryRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException::new);
		product.setCategory(category);
		productRepo.save(product);
	}
	
	@Transactional
	@Caching(evict = {
	@CacheEvict(cacheNames = "products", key = "#id"),
	@CacheEvict(cacheNames = "product-search", allEntries = true)
	})
	public void delete(int id) {
		productRepo.deleteById(id);
	}
	
	public PageDTO<ProductDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> pageRS = productRepo.searchByName(name, pageable);
		PageDTO<ProductDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<ProductDTO> productDTOs = pageRS.get().
				map(product -> new ModelMapper().
				map(product, ProductDTO.class)).
				collect(Collectors.toList());
		pageDTO.setContents(productDTOs); // set vao pageDTO)
		return pageDTO;
	}
	
	public ProductDTO getById(int id) {
		Product product = productRepo.findById(id).orElseThrow(NoResultException::new);
		ProductDTO productDTO = new ModelMapper().map(product, ProductDTO.class);
		return productDTO;
	}
}
