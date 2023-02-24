package com.example.demo;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtTokenService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
	@Autowired
	private JwtTokenService jwtTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		// doc token tu header
		String token = resolveToken(httpServletRequest);
		
			// verify token
			if(token != null ) {
				// có token rồi thì lấy Username, gọi db lên user
				String username = jwtTokenService.getUsername(token);
				if(username != null) {
					Authentication auth = jwtTokenService.getAuthentication(username);
					// set vào Context để có đăng nhập rồi 
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					SecurityContextHolder.clearContext();
					httpServletResponse.sendError(401, "No Authorization");
					return;
				}
			}
		filterChain.doFilter(httpServletRequest, httpServletResponse);	
	}
	
	// lấy token từ request gửi lên: header, params, form
	private String resolveToken(HttpServletRequest httpServletRequest) {
		String bearerToken = httpServletRequest.getHeader("Authorization");
		System.out.println(bearerToken);
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;	
	}
	
	
}
