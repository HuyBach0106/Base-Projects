package com.example.demo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.service.GroupService;
import com.example.demo.dto.CourseDTO;

@RestController
@RequestMapping("/api/group")
public class GroupController {
	@Autowired
    GroupService groupService;

    @PostMapping("/new")
    @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDTO<GroupDTO> create(@RequestBody @Valid GroupDTO groupDTO) {
		groupService.createGroup(groupDTO);
		return ResponseDTO.<GroupDTO>builder().
				status(200).data(groupDTO).build();
	}
    
    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        groupService.deleteGroup(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<GroupDTO> get(@PathVariable("id") int id){
    	GroupDTO groupDTO = groupService.getById(id);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
        //jackson add on, gson
    }
    
    @PostMapping("/search")
    public ResponseDTO<PageDTO<GroupDTO>> search(
            @RequestParam(name ="id", required=false) Integer id,
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "studentId", required = false) Integer studentId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        PageDTO<GroupDTO> pageRS = null;

        if(id == null || id.equals("")){
            pageRS = groupService.searchGroupByName(name,page,size);

        }
        else if(name == "" || name.isEmpty()){
            pageRS = groupService.searchGroupById(id,page,size);

        } else if(name != "" && id != null && userId != null){
            pageRS = groupService.searchGroupByUser(userId,page,size);

        }
        return ResponseDTO.<PageDTO<GroupDTO>>builder().status(200).data(pageRS).build();
    }
    
    @PostMapping("/update")
    public ResponseDTO<Void> update(@ModelAttribute GroupDTO groupDTO) throws IllegalStateException, IOException {
        groupService.updateGroup(groupDTO);
        return ResponseDTO.<Void>builder().status(200).build();
    }
}
