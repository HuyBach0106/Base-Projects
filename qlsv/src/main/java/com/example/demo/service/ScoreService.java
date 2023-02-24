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
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ScoreDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Score;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepo;
import com.example.demo.repository.ScoreRepo;
import com.example.demo.repository.StudentRepo;
import com.example.demo.repository.UserRepo;

@Service
public class ScoreService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	CourseRepo courseRepo;
	
	@Autowired
	ScoreRepo scoreRepo;
	
	@Transactional
	public void createScore(ScoreDTO scoreDTO) {
        Course course = courseRepo.findById(scoreDTO.getCourseId()).orElseThrow(NoResultException::new);
        Student student = studentRepo.findById(scoreDTO.getStudentId()).orElseThrow(NoResultException::new);
        Score score = new Score();
        score.setScore(scoreDTO.getScore());
        score.setCourse(course);
        score.setStudent(student);
        scoreRepo.save(score);
	}
	
	@Transactional
    public PageDTO<ScoreDTO> searchById(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchById(id, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }
	
	@Transactional
	public PageDTO<ScoreDTO> searchByCourseId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchByCourseId(id, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }
	
	@Transactional
	public PageDTO<ScoreDTO> searchByStudentId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchByStudentId(id, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }
	
	@Transactional
    public PageDTO<ScoreDTO> searchByScore(Double score, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchByScore(score, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }
	
	@Transactional
	public PageDTO<ScoreDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.findAll(pageable);

        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }
	
	@Transactional
	public ScoreDTO getScoreById(int id) {
		Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
		ScoreDTO scoreDTO = new ModelMapper().map(score, ScoreDTO.class);
		return scoreDTO;
	}
	
	@Transactional
	public void updateScore(ScoreDTO scoreDTO, CourseDTO courseDTO, StudentDTO studentDTO) {
		Score score = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);
		Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
		Student student = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
		score.setId(scoreDTO.getId());
		score.setCourse(course);
		score.setStudent(student);
		scoreRepo.save(score);
	}
	
	@Transactional
	public void deleteScore(int id) {
		scoreRepo.deleteById(id);
	}
	
	@Transactional
    public void deleteAll(List<Integer> ids) {
        scoreRepo.deleteAllById(ids);
    }
}
