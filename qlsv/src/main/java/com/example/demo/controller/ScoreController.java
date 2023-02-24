package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ScoreDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.service.ScoreService;

import groovyjarjarpicocli.CommandLine.Model;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/score")
public class ScoreController {
	@Autowired
    ScoreService scoreService;

    @PostMapping("/new")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<ScoreDTO> add(@ModelAttribute ScoreDTO scoreDTO){
    	scoreService.createScore(scoreDTO);
    	return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO).build();
    }

    @PostMapping("/new-json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<ScoreDTO> create(@RequestBody ScoreDTO scoreDTO){
        scoreService.createScore(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO).build();
    }

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        scoreService.deleteScore(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<ScoreDTO> get(@PathVariable("id") int id){
    	ScoreDTO scoreDTO = scoreService.getScoreById(id);
    	return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO).build();
        //jackson add on, gson
    }

    @PostMapping("/update") // ?id=1
	public ResponseDTO<Void> update(@ModelAttribute ScoreDTO scoreDTO, CourseDTO courseDTO, StudentDTO studentDTO) throws IllegalStateException, IOException {
		scoreService.updateScore(scoreDTO, courseDTO, studentDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
    
    @PostMapping("/search")
    public ResponseDTO<PageDTO<ScoreDTO>> search(
            @RequestParam(name ="id", required=false) Integer id,
            @RequestParam(name ="score", required=false) Double score,
            @RequestParam(name = "courseId", required = false) Integer courseId,
            @RequestParam(name = "studentId", required = false) Integer studentId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        PageDTO<ScoreDTO> pageRS = null;
        if(id != null){
            pageRS = scoreService.searchById(id,page,size);
        }else if(score != null){
            pageRS = scoreService.searchByScore(score,page,size);
        }else if(courseId != null){
            pageRS = scoreService.searchByCourseId(courseId,page,size);
        } else if(studentId != null){
            pageRS = scoreService.searchByStudentId(studentId,page,size);
        }else{
            pageRS = scoreService.getAll(page,size);
        }
        return ResponseDTO.<PageDTO<ScoreDTO>>builder().status(200).data(pageRS).build();
    }
}
