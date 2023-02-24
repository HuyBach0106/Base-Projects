package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepo;
import com.example.demo.repository.ScoreRepo;
import com.example.demo.dto.PageDTO;

@Service
public class CourseService {
	@Autowired
	ScoreRepo scoreRepo;
	
	@Autowired
	CourseRepo courseRepo;
	
	@Transactional
	public void createCourse(CourseDTO courseDTO) {
		Course course = new Course();
		course.setName(courseDTO.getName());
		courseRepo.save(course);
	}
	
	@Transactional
	public PageDTO<CourseDTO> searchCourseByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Course> pageRS = courseRepo.searchByName(name, pageable);
		PageDTO<CourseDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<CourseDTO> courseDTOs = new ArrayList<>();
		for(Course course : pageRS.getContent()) {
			courseDTOs.add(new ModelMapper().map(course, CourseDTO.class));
		}
		pageDTO.setContents(courseDTOs); // set vào PageDTO
		return pageDTO;
	}
	
	@Transactional
	public CourseDTO getCourseById(int id) {
		Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setId(course.getId());
		courseDTO.setName(course.getName());
		return courseDTO;
	}
	
	@Transactional
	 public List<CourseDTO> getAll() {
	        List<CourseDTO> courseDTOS = new ArrayList<>();
	        List<Course> courses = courseRepo.findAll();
	        for(Course c:courses){
	            CourseDTO courseDTO = new ModelMapper().map(c,CourseDTO.class);
	            courseDTOS.add(courseDTO);
	        }
	        return courseDTOS;
	    }
	
	@Transactional
	public void updateCourse(CourseDTO courseDTO) {
		Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
		 if(course != null){
			 course.setId(courseDTO.getId());
	         course.setName(courseDTO.getName());   
	         courseRepo.save(course);
	     }
	}
	
	@Transactional
	public void deleteCourseById(int id) {
		courseRepo.deleteById(id);	
	}
	
	@Transactional
    public void deleteAll(List<Integer> ids) {
        courseRepo.deleteAllById(ids);
    }
}
