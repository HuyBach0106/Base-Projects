package com.java.project3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.java.project3.dto.MessageDTO;

@Service
public class MessageService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@KafkaListener(id = "notificationGroup", topics = "notification")
	public void listen(MessageDTO messageDTO) {
		log.info("Received: " + messageDTO.getToName());
	}
}
