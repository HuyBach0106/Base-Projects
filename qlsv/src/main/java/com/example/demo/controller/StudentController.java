package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/new")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDTO<StudentDTO> create(@RequestBody StudentDTO studentDTO) {
		studentService.createStudent(studentDTO);
		return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
	}
	
	@PostMapping("/new-json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<StudentDTO> add(@RequestBody StudentDTO studentDTO){
        studentService.createStudent(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
    }
	
	@PostMapping("/search")
    public ResponseDTO<PageDTO<StudentDTO>> search(
    		@RequestParam(name = "name", required = false) String name,
            @RequestParam(name ="studentCode", required=false) String studentCode,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        studentCode = studentCode == null ?"" : studentCode;
        PageDTO<StudentDTO> pageRS = null;
        pageRS = studentService.searchStudent(name, studentCode, page, size);

        return ResponseDTO.<PageDTO<StudentDTO>>builder().
        		status(200).data(studentService.searchStudent(name, studentCode, page, size)).build();
    }
	
	@GetMapping("/get/{id}")
	public ResponseDTO<StudentDTO> getStudent(@PathVariable("id") int id) {
		// phải new 1 biến studentDTO để lưu cái vừa get bằng id ra để in ra 
		StudentDTO studentDTO = studentService.getStudentById(id);
		return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
	}
	
	@PutMapping("/update")
	public ResponseDTO<StudentDTO> update(@RequestBody StudentDTO studentDTO) {
		studentService.updateStudent(studentDTO);
		return ResponseDTO.<StudentDTO>builder().status(200).build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<StudentDTO> delete(@PathVariable("id") int id) {
		studentService.deleteStudent(id);
		return ResponseDTO.<StudentDTO>builder().status(200).build();
	}
}
