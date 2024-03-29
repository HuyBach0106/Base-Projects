package com.java.project3;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("!!!!!!");
		System.out.println(request.getServletPath());
		System.out.println(request.getMethod());
		String path = request.getServletPath();
		
		// entity AUTHORITY (path, role)
		if (path.equals("/api/user/search")) { // DUNG THU VIEN JAVA REGEX
			// role admin
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
				List<String> roles = auth.getAuthorities().stream().map(p -> p.getAuthority()).
						collect(Collectors.toList());
				if(!roles.contains("ROLE_ADMIN")) {
					throw new AccessDeniedException("");
				}
				return true; // để chạy qua
			}
			
			throw new AccessDeniedException("");
		}
		return true;
	}
	
}
