package com.java.project3.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.java.project3.dto.ProductDTO;
import com.java.project3.dto.ResponseDTO;
import com.java.project3.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {
	@Autowired
	ProductService productService;
	
	// jackson - GRPC
	@PostMapping("/new")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDTO<ProductDTO> createProduct(@ModelAttribute @Valid ProductDTO productDTO) throws IllegalStateException, IOException {
		if(productDTO.getFile() != null && !productDTO.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/user/";
			if(!(new File(UPLOAD_FOLDER).exists())) {
				new File(UPLOAD_FOLDER).mkdir();
			}
			String filename = productDTO.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;
			
			File newFile = new File(UPLOAD_FOLDER + newFilename);
			productDTO.getFile().transferTo(newFile);
			productDTO.setImage(newFilename); // save to db
			
		}
		productService.create(productDTO);
		return ResponseDTO.
				<ProductDTO>builder().
				status(200).data(productDTO).
				msg("Add product successfully").build();
	}
	
	@GetMapping("/{id}")
	public ResponseDTO<ProductDTO> getProductById(@PathVariable int id){
		ProductDTO productDTO = productService.getById(id);
		return ResponseDTO.<ProductDTO>builder().
				status(200).data(productDTO).build();
	}
	
//	@DeleteMapping("/{id}")
//	public ResponseDTO 
}
