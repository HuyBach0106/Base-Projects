package com.java.project3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.project3.repo.UserRepo;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.java.project3.entity.User user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("not found");
		}	
		
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		
		for(String role : user.getRoles()) {
			list.add(new SimpleGrantedAuthority(role));
		}
		
		// tạo user của Security
		// user đăng nhập hiện tại
		User currentUser = new User(username, user.getPassword(), list);
		
		return currentUser;
	}
}
