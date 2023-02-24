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

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepo;
import com.example.demo.repository.UserRoleRepo;

@Service
public class UserRoleService {
	@Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    UserRepo userRepo;
    
    @Transactional
	public void create(UserRoleDTO userRoleDTO) {
        UserRole userRole = new UserRole();
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void update(UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepo.findById(userRoleDTO.getId()).orElseThrow(NoResultException::new);
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    //delete by user role id
    public void delete(int id){
        userRoleRepo.deleteById(id);
    }

    @Transactional
    //delete by list user role id
    public void deleteAll(List<Integer> ids){
        userRoleRepo.deleteAllById(ids);
    }

    @Transactional
    public void deleteByUserId(int userId){
        userRoleRepo.deleteByUserId(userId);
    }
    
    @Transactional
    public UserRoleDTO getById(int id) {
        UserRole userRole = userRoleRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(userRole,UserRoleDTO.class);
    }

    @Transactional
    public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<UserRole> pageRS = userRoleRepo.searchByUserId(userId,pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<UserRoleDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for(UserRole userRole : pageRS.getContent()){
            userRoleDTOs.add(new ModelMapper().map(userRole,UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOs);
        return pageDTO;
    }

    @Transactional
    public PageDTO<UserRoleDTO> searchByRole(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<UserRole> pageRS = userRoleRepo.searchByRole(role,pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<UserRoleDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for(UserRole userRole : pageRS.getContent()){
            userRoleDTOs.add(new ModelMapper().map(userRole,UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOs); //set vao page dto
        return pageDTO;
    }

    @Transactional
    public PageDTO<UserRoleDTO> searchByUserIdRole(int userId, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<UserRole> pageRS = userRoleRepo.searchByUserIdRole(userId,role,pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<UserRoleDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for(UserRole userRole : pageRS.getContent()){
            userRoleDTOs.add(new ModelMapper().map(userRole,UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOs); //set vao page dto
        return pageDTO;
    }
}
