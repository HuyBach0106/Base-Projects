package com.java.project3.controller;

import java.sql.SQLException;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.java.project3.dto.ResponseDTO;

@RestControllerAdvice
public class ExceptionController {
	// log org.slf4j.Logger
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@ExceptionHandler({AccessDeniedException.class}) 
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ResponseDTO<Void> accessDeny(Exception ex) {
		logger.info("ex", ex);
		return ResponseDTO.<Void>builder().status(403).msg("Deny").build();
	}
	
}