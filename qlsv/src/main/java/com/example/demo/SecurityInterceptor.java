package com.example.demo;


import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepo;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

	// Chỉ Admin mới được quyền search
	// Linh động hơn JwtFilter, có thể if else, đọc từ db, ....
	// Mục đích Interceptor chặn hết các Request gửi lên
	@Override
	public boolean preHandle(HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("!!!!!!");
		System.out.println(request.getServletPath());
		System.out.println(request.getMethod());
		String path = request.getServletPath();
		
		// Thực tế nên tạo 1 entity AUTHORITY (path, role) tương ứng đường dẫn
		if (path.equals("/api/user/search")) { // THƯ VIỆN JAVA REGEX chuyên để so sánh String
			// role admin
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
				List<String> roles = auth.getAuthorities().stream().map(p -> p.getAuthority()).
						collect(Collectors.toList());
				if(!roles.contains("ROLE_ADMIN")) {
					throw new AccessDeniedException("");
				}
//				return true; 
			}
			throw new AccessDeniedException("");
		}
		return true;
	}
	
}
