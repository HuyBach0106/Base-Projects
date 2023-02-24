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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepo;
import com.example.demo.repository.UserRoleRepo;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserRoleRepo userRoleRepo;
	
	@Transactional
	public void createUser(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class); // convert dto -> entity
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepo.save(user);
		
		List<UserRoleDTO> userRoleDTOS = userDTO.getUserRoles();
        {
            for (UserRoleDTO userRoleDTO : userRoleDTOS){
                if(userRoleDTO.getRole()!=null){
                    //save to db
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(userRoleDTO.getRole());
                    userRoleRepo.save(userRole);
                }
            }
        }
	}
	
	@Transactional
	public PageDTO<UserDTO> searchUserByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> pageRS = userRepo.searchByName("%" + name + "%", pageable);
		PageDTO<UserDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User user : pageRS.getContent()) {
			userDTOs.add(new ModelMapper().map(user, UserDTO.class)); // map từ Entity sang DTO để trả về Response cho user
		}
		pageDTO.setContents(userDTOs); // set list kết quả vừa có vào pageDTO
		return pageDTO; // trả về kết quả 
	}
	
	@Transactional
	public UserDTO getUserById(int id) {
		User user = userRepo.findById(id).orElseThrow(NoResultException::new);
		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		return userDTO;
	}
	
	@Transactional
	public List<UserDTO> getAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userRepo.findAll();
        for(User u:users){
            UserDTO userDTO = new ModelMapper().map(u,UserDTO.class);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
	
	@Transactional
	public void updateUser(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		user.setName(userDTO.getName());
		user.setUsername(userDTO.getUsername());
		user.setBirthdate(userDTO.getBirthdate());
		user.setPassword(userDTO.getPassword());
		user.setAvatar(userDTO.getAvatar());	
		userRepo.save(user);
	}
	
	@Transactional
	public void updatePassword(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setPassword(userDTO.getPassword());

        userRepo.save(user);
    }
	
	@Transactional
	public void deleteUserById(int id) {
		User user = userRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda
        userRoleRepo.deleteByUserId(user.getId());
        userRepo.deleteById(id);
	}
	
	@Transactional
	public void deleteAll(List<Integer> ids) {
		userRepo.deleteAllById(ids);	
	}
}
