package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@PostMapping("/new")
	public ResponseDTO<CourseDTO> create(@ModelAttribute CourseDTO courseDTO) {
		courseService.createCourse(courseDTO);
		return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<CourseDTO>> search(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "name", required = false) String name, 
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page) {
			
			size = size == null ? 10 : size;
			page = page == null ? 0 : page;
			name = name == null ? "" : name;
			PageDTO<CourseDTO> pageRS = null;
		
			pageRS = courseService.searchCourseByName(name, page, size);
			
			return ResponseDTO.<PageDTO<CourseDTO>>builder().status(200).data(pageRS).build();
	}
	
	@GetMapping("/get/{id}")
	public ResponseDTO<CourseDTO> get(@PathVariable("id") int id) {
		CourseDTO courseDTO = courseService.getCourseById(id);
		return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build();
	}
	
	@PostMapping("/update") // ?id=1
	public ResponseDTO<Void> update(@ModelAttribute CourseDTO courseDTO) throws IllegalStateException, IOException {
		courseService.updateCourse(courseDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@DeleteMapping("/delete") 
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		courseService.deleteCourseById(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}
}