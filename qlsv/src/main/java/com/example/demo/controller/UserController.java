package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/new")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDTO<UserDTO> create(@ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {
		if(userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/user/";
			String filename = userDTO.getFile().getOriginalFilename();
			String extension = filename.substring(filename.lastIndexOf("."));
			String newFilename = UUID.randomUUID().toString() + extension;
			File newFile = new File(UPLOAD_FOLDER + filename);
			userDTO.getFile().transferTo(newFile);
			userDTO.setAvatar(filename);
		}
		userService.createUser(userDTO); // save to db
		return ResponseDTO.<UserDTO>builder().
				status(200).data(userDTO).build();
	}
	
	@GetMapping("/search")
	public ResponseDTO<PageDTO<UserDTO>> search(
		@RequestParam(name = "id", required = false) Integer id,
		@RequestParam(name = "name", required = false) String name, 
		@RequestParam(name = "size", required = false) Integer size,
		@RequestParam(name = "page", required = false) Integer page) {
			
		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		name = name == null ? "" : name;
		
		PageDTO<UserDTO> pageRS = null;
		pageRS = userService.searchUserByName(name, page, size);
		
		return ResponseDTO.<PageDTO<UserDTO>>builder()
				.status(200).data(pageRS).build();
	}
	
	@GetMapping("/get/{id}") 
	public ResponseDTO<UserDTO> get(@PathVariable("id") int id) {
		UserDTO userDTO = userService.getUserById(id);
		return ResponseDTO.<UserDTO>builder().
				status(200).data(userDTO).build();
	}
	
	@PostMapping("/update") // ?id=1
	public ResponseDTO<Void> update(@ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {
		if(!userDTO.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/"; // save file to D
			//Read file
			String fileName = userDTO.getFile().getOriginalFilename();
			//Save file
			File newFile = new File(UPLOAD_FOLDER + fileName);
			
			userDTO.getFile().transferTo(newFile);
			//Save to Database
			userDTO.setAvatar(fileName);
		}
		userService.updateUser(userDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@DeleteMapping("/delete") 
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		userService.deleteUserById(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}
}
