package com.digitinary.customers.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListenerService {
	
	private final JmsTemplate jmsTemplate;
	
	// Listen for messages on the same queue/topic as the publisher
	@JmsListener(destination = "status-changed-queue")
	public void consumeMessage(String message) {
		log.info("CUSTOMERS service");	
		log.info("Received message: " + message);	
	}
	
}
