package com.java.project3.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.project3.service.JwtTokenService;

@RestController
@RequestMapping("/api")
public class LoginAPI {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	
	@GetMapping("/me") // lấy ng dùng hiện tại
	public Principal me(Principal p) {
		p.getName();	
		return p;
	}
	
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		return jwtTokenService.createToken(username); // trả về string
	}
}
