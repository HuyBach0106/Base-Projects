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
import org.springframework.util.StringUtils;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.StudentRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.entity.UserRole;

@Service
public class StudentService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	StudentRepo studentRepo;
	
	@Transactional
	public void createStudent(StudentDTO studentDTO) {
//		User user = userRepo.findById(studentDTO.getUserId()).orElseThrow(NoResultException::new);
//	    Student s = new Student();
//	    s.setId(studentDTO.getId());
//	    s.setStudentCode(studentDTO.getStudentCode());
//	    s.setUser(user);
//	    studentRepo.save(s);
		User user = userRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
		
		for(UserRole userRole : user.getUserRoles()) {
			if(userRole.getRole().equals("ROLE_STUDENT")) {
				Student student = new Student();
				student.setStudentCode(studentDTO.getStudentCode());
				student.setId(studentDTO.getId());
				studentRepo.save(student);
				return; 
			}
		}
	}
		
	@Transactional	
	public StudentDTO getStudentById(int id) {
		Student student = studentRepo.getStudentById(id);
		StudentDTO studentDTO = new ModelMapper().map(student, StudentDTO.class);
		return studentDTO;
	}
	
	public List<StudentDTO> getAll() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> students = studentRepo.getAll();
        for(Student s:students){
            StudentDTO studentDTO = new ModelMapper().map(s,StudentDTO.class);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }
	
	@Transactional
	public PageDTO<StudentDTO> searchStudent(String name, String studentCode, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Student> pageRS = null; 
		if(StringUtils.hasText(name) && StringUtils.hasText(studentCode)) {
			pageRS = studentRepo.searchByNameAndCode(studentCode, name, pageable);
		} else if(StringUtils.hasText(name) && StringUtils.isEmpty(studentCode) ){
			pageRS = studentRepo.searchStudentByName("%" + name + "%", pageable);
		} else if(StringUtils.hasText(studentCode)) {
			pageRS = studentRepo.searchByStudentCode(studentCode, pageable);
		} else { 
			pageRS = studentRepo.findAll(pageable);
		}
		
		PageDTO<StudentDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<StudentDTO> studentDTOs = new ArrayList<>();
		for(Student student : pageRS.getContent()) {
			studentDTOs.add(new ModelMapper().map(student, StudentDTO.class));
		}
		pageDTO.setContents(studentDTOs);
		return pageDTO;
	}
	
	@Transactional
	public void updateStudent(StudentDTO studentDTO) {
		Student student = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
		student.setId(studentDTO.getId());
		student.setStudentCode(studentDTO.getStudentCode());
		studentRepo.save(student);
	}
	
	@Transactional
	public void deleteStudent(int id) {
		studentRepo.deleteById(id);
	}
}
