package com.example.demo.controller;

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
import com.example.demo.dto.StudentDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.service.UserRoleService;


import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleController {
	@Autowired
    UserRoleService userRoleService;

    @PostMapping("/new")
    @ResponseStatus(value= HttpStatus.CREATED)
    public void add(@ModelAttribute UserRoleDTO userRoleDTO){
        userRoleService.create(userRoleDTO);
    }

    @PostMapping("/new-json")
    @ResponseStatus(value= HttpStatus.CREATED)
    public void create(@RequestBody UserRoleDTO userRoleDTO){
        userRoleService.create(userRoleDTO);
    }

    @DeleteMapping("/delete") //delete?id=10
    public void delete(@RequestParam("id") int id ){
        userRoleService.delete(id);
    }

    @GetMapping("/get/{id}") ///get/10
    public UserRoleDTO get(@PathVariable("id") int id){
        return userRoleService.getById(id);
        //jackson add on, gson
    }

    @PostMapping("/search")
    public PageDTO<UserRoleDTO> search(
            @RequestParam(name="id", required = false) Integer id,
            @RequestParam(name ="role", required=false) String role,
            @RequestParam(name ="userId", required=false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        role = role == null ?"" : role;
        userId = userId == null ? null : userId;
        PageDTO<UserRoleDTO> pageRS = null;
        if(userId == null){
            pageRS = userRoleService.searchByRole("%"+role+"%",page,size);
        }
          else if(role == "" || role.isEmpty()){
            pageRS = userRoleService.searchByUserId(userId,page,size);
        } else if(role != "" && userId != null){
            pageRS = userRoleService.searchByUserIdRole(userId,role,page,size);
        }
        return pageRS;
    }

}
