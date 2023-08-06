package com.java.project3.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.java.project3.dto.MessageDTO;

@Component
public class PollingService {
			
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	@Scheduled(fixedDelay = 1000)
	public void producer() {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setId(1);
		messageDTO.setToName("Johan");
		
		kafkaTemplate.send("notification", messageDTO);
	}
}
