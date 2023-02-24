package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	// B1: Xác thực	username trong bảng User lưu trong database -> LoginService
	// Sau này xác thực bằng Googe, Facebook thì customize lại đoạn này
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).
		passwordEncoder(new BCryptPasswordEncoder()); 
		// encode mật khẩu ra 1 string ngẫu nhiên
	}
	
	// Check mật khẩu nếu đúng thì sẽ tạo ra Token
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// B2: Sau khi xác thực thì tới phân quyền, lọc token của request
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**")
				.hasAnyAuthority("ROLE_ADMIN", "ROLE_STUDENT", "ROLE_MEMBER").antMatchers("/member/**")
				.authenticated().anyRequest()
				.permitAll().and().csrf().disable().sessionManagement().
				sessionCreationPolicy(SessionCreationPolicy.NEVER).and().httpBasic();
		
		// APPLY JWT
		// JWT đóng vai trò generate ra 1 String và Jwt sẽ Encode và Decode cho mình
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
