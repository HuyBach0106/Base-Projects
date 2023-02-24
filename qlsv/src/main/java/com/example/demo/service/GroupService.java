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

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.repository.GroupRepo;
import com.example.demo.repository.UserRepo;

@Service
public class GroupService {
	@Autowired
	GroupRepo groupRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Transactional
	public void createGroup(GroupDTO groupDTO) {
//		Group group = new Group();
//        group.setName(groupDTO.getName());
//        List<User> users = userRepo.findAllById(groupDTO.getUserIds());
//        group.setUsers(users);
//        groupRepo.save(group);
		Group group = new Group();
		group.setName(groupDTO.getName());
		
		List<User> users = new ArrayList<>();
		
		for(Integer userId : groupDTO.getUserIds()) {
			User user = userRepo.findById(userId).orElseThrow(NoResultException::new);
			
			users.add(user);
		}
		group.setUsers(users);
		groupRepo.save(group);
	}
	
	@Transactional
	public PageDTO<GroupDTO> searchGroupByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Group> pageRS = groupRepo.searchByName(name, pageable);
		PageDTO<GroupDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		List<GroupDTO> groupDTOs = new ArrayList<>();
		for(Group group : pageRS.getContent()) {
			groupDTOs.add(new ModelMapper().map(group, GroupDTO.class));
		}
		pageDTO.setContents(groupDTOs);
		return pageDTO;
	}
	
	@Transactional
    public PageDTO<GroupDTO> searchGroupById(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Group> pageRS = groupRepo.searchById(id,pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<GroupDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOS = new ArrayList<>();
        for(Group group : pageRS.getContent()){
            groupDTOS.add(new ModelMapper().map(group,GroupDTO.class));
        }

        pageDTO.setContents(groupDTOS);
        return pageDTO;
    }
	
	@Transactional
    public PageDTO<GroupDTO> searchGroupByUser(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Group> pageRS = groupRepo.searchByUser(userId,pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<GroupDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOS = new ArrayList<>();
        for(Group group : pageRS.getContent()){
            groupDTOS.add(new ModelMapper().map(group,GroupDTO.class));
        }

        pageDTO.setContents(groupDTOS);
        return pageDTO;
    }
	
	@Transactional
    public GroupDTO getById(int id) {
       Group group = groupRepo.findById(id).orElseThrow(NoResultException::new);
       return new ModelMapper().map(group,GroupDTO.class);
    }
	
	@Transactional
	public void updateGroup(GroupDTO groupDTO) {
		Group group = groupRepo.findById(groupDTO.getId()).orElseThrow(NoResultException::new);
		group.setName(groupDTO.getName());
        List<User> users = userRepo.findAllById(groupDTO.getUserIds());
        group.setUsers(users);
        groupRepo.save(group);
	}
	
	@Transactional
	public void deleteGroup(int id) {
		groupRepo.deleteById(id);
	}
	
	@Transactional
    public void deleteAll(List<Integer> ids) {
        groupRepo.deleteAllById(ids);
    }
}
