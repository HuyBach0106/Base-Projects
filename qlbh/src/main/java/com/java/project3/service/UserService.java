package com.java.project3.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.project3.dto.PageDTO;
import com.java.project3.dto.UserDTO;
import com.java.project3.dto.UserRoleDTO;
import com.java.project3.entity.User;
import com.java.project3.repo.CategoryRepo;
import com.java.project3.repo.UserRepo;


//Service là lớp trung gian trong mô hình 3 lớp DTO, Entity, Service
// DTO chứa những thuộc tính cơ bản, ít quan hệ
@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Transactional
	public void create(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class); // convert dto -> entity
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		//Transactional dùng để rollback lại
		userRepo.save(user);
	}
	
	@Transactional
	public void update(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		user.setName(userDTO.getName());
		user.setBirthdate(userDTO.getBirthdate());
		user.setRoles(userDTO.getRoles());
		user.setEmail(userDTO.getEmail());	
		if(userDTO.getAvatar() != null) {
			user.setAvatar(userDTO.getAvatar());
		}
		userRepo.save(user);
	}
	
	@Transactional
	public void update2(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(UserDTO.class, User.class).addMappings(map -> {
			map.skip(User::setPassword); 
			if(userDTO.getAvatar() == null) {
				map.skip(User::setAvatar);
			}
			})
		.setProvider(p -> user);
		User saveUser = mapper.map(userDTO, User.class);
		user.setName(userDTO.getName());
		user.setBirthdate(userDTO.getBirthdate());
		user.setRoles(userDTO.getRoles());
		user.setEmail(userDTO.getEmail());	
		
		userRepo.save(user);
	}
	
	@Transactional
	public void updatePassword(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//		user.setPassword(userDTO.getPassword());
		userRepo.save(user);
	}
	
	@Transactional
	public PageDTO<UserDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		Page<User> pageRS = userRepo.searchByName("%" + name + "%", pageable);
		
		PageDTO<UserDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User user : pageRS.getContent()) {
			userDTOs.add(new ModelMapper().map(user, UserDTO.class));
		}
		
		pageDTO.setContents(userDTOs); // set vào page DTO
		return pageDTO;
	}
	
	
	public UserDTO getById(int id) {
		User user = userRepo.findById(id).orElseThrow(NoResultException::new); // java lambda 8
//		UserDTO userDTO = new UserDTO();
//		
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setBirthdate(user.getBirthdate());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAvatar(user.getAvatar());
//		userDTO.setCreatedAt(user.getCreatedAt());
//		
		//modelmapper thư viện hay dùng để convert dữ liệu set get cho nhanh, nếu ít thì nên tự set
		// lấy bên User rồi set sang UserDTO
		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		
		return userDTO;
	}
}
