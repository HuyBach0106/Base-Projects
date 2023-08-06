package com.java.project3.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.project3.dto.CategoryDTO;
import com.java.project3.dto.PageDTO;
import com.java.project3.dto.ResponseDTO;
import com.java.project3.dto.UserDTO;
import com.java.project3.dto.UserRoleDTO;
import com.java.project3.service.CategoryService;
import com.java.project3.service.UserService;

@RestController
@RequestMapping("/admin/api/category")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;
	
	@DeleteMapping("/delete") 
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		categoryService.delete(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@GetMapping("/get/{id}") 
	public ResponseDTO<CategoryDTO> get(@PathVariable("id") int id) {
		CategoryDTO categoryDTO = categoryService.getById(id);
//		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
//		responseDTO.setStatus(200);
//		responseDTO.setData(userDTO);
		return ResponseDTO.<CategoryDTO>builder().
				status(200).data(categoryDTO).build();
	}
	
	@GetMapping("/search")
	public ResponseDTO<PageDTO<CategoryDTO>> search(
		@RequestParam(name = "id", required = false) Integer id,
		@RequestParam(name = "name", required = false) String name, 
		@RequestParam(name = "size", required = false) Integer size,
		@RequestParam(name = "page", required = false) Integer page) {
			
		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		name = name == null ? "" : name;
		PageDTO<CategoryDTO> pageRS = null;
		
		pageRS = categoryService.searchByName(name, page, size);
	
		return ResponseDTO.<PageDTO<CategoryDTO>>builder()
				.status(200).data(pageRS).build();
	}
	
	@PostMapping("/new")
	public ResponseDTO<CategoryDTO> create(@ModelAttribute CategoryDTO categoryDTO) throws IllegalStateException, IOException {
		categoryService.create(categoryDTO);
		return ResponseDTO.<CategoryDTO>builder().
				status(200).data(categoryDTO).build();
	}
	
	@PostMapping("/edit") // ?id=1
	public ResponseDTO<Void> edit(@ModelAttribute CategoryDTO categoryDTO) throws IllegalStateException, IOException {
		categoryService.update(categoryDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
}
