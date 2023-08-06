package com.java.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class AccountController {
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	
}
