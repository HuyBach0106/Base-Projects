package com.java.project3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.project3.dto.PageDTO;
import com.java.project3.dto.ResponseDTO;
import com.java.project3.dto.UserDTO;
import com.java.project3.entity.User;
import com.java.project3.repo.UserRepo;
import com.java.project3.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/new")
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
//		userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		userService.create(userDTO); // save to db
		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
	}
	
	// /user/download?filename=abc.jpg
	@GetMapping("/download/{filename}")
	public void download(@PathVariable("filename") String filename, 
			HttpServletResponse response) throws IOException {
		final String UPLOAD_FOLDER = "D:/file/";
		File file = new File(UPLOAD_FOLDER + filename);
		
		//java.nio.file.Files
		Files.copy(file.toPath(), response.getOutputStream());
	}
	
	@GetMapping("/search")
//	@Secured({"ROLE_ADMIN"})
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	@PostAuthorize("returnObject != null || #id != null")
	public ResponseDTO<PageDTO<UserDTO>> search(@RequestParam(name = "id", required = false) Integer id, 
		@RequestParam(name = "name", required = false) String name, 
		@RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start, 
		@RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
		@RequestParam(name = "size", required = false) Integer size,
		@RequestParam(name = "page", required = false) Integer page, Model model) {
		
		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		PageDTO<UserDTO> pageRS = null;	
		if(StringUtils.hasText(name)) {
			pageRS = userService.searchByName("%" + name + "%", page, size);
		}
		return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).build();	
	}
	
	public ResponseDTO<Void> get(@PathVariable("id") int id) {
		UserDTO userDTO = userService.getById(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@PutMapping("/update")
	public ResponseDTO<Void> update(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException {
		if(userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/user/";
			String filename = userDTO.getFile().getOriginalFilename();
			String extension = filename.substring(filename.lastIndexOf("."));
			String newFilename = UUID.randomUUID().toString() + extension;
			File newFile = new File(UPLOAD_FOLDER + filename);
			userDTO.getFile().transferTo(newFile);
			userDTO.setAvatar(filename);
		}
		userService.update(userDTO); // save to db
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@PutMapping("/update/password")
	public ResponseDTO<Void> updatePassword(@RequestBody @Valid UserDTO userDTO) {
		userService.updatePassword(userDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
//	@DeleteMapping("/delete/{")
//	pubblic 
}
