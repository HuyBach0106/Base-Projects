package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {	  // jwt.secret = ...
	@Value("${jwt.secret:123456") // giá trị mặc định nếu ko set ở application.properties
	private String secretKey;
	
	private long validityInMilliseconds = 3600000; // 1h
	
	@Autowired
	UserDetailsService userDetailsService;
	
	public String createToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);
		
		Date now = new Date();
		Date expiredTime = new Date(now.getTime() + validityInMilliseconds);
		String accessToken = Jwts.builder()
							.setClaims(claims)
							.setIssuedAt(now)
							.setExpiration(expiredTime)
							.signWith(SignatureAlgorithm.HS256, secretKey)
							.compact();
		return accessToken;
	}
	
	public boolean validateToken(String token) {
		try {
		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// lấy username
	public String getUsername(String token) {
		try {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
				.getBody().getSubject();
		} catch (Exception e) {
			e.printStackTrace(); // xem lỗi Token là gì
			return null;
		}
	}
	// lấy quyền, lấy User Detail từ database
	// hàm này nên viết ở Login Service 			
	public Authentication getAuthentication(String username) {
		UserDetails userDetails = userDetailsService.
				loadUserByUsername((username));
		return new UsernamePasswordAuthenticationToken(userDetails, "",
				userDetails.getAuthorities());
	}
}
