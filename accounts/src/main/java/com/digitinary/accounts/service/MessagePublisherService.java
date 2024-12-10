package com.digitinary.accounts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessagePublisherService {

	private final String destination;	
	private final JmsTemplate jmsTemplate;

    public MessagePublisherService(@Value("${jms.destination}") String destination, JmsTemplate jmsTemplate) {
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
    }
	
	public void sendMessageAccountStatusChanged(Object message) {
		this.sendMessage(destination, message);
	}
	
	private void sendMessage(String destination, Object message) {
		this.jmsTemplate.convertAndSend(destination, message);
//		log.info("", message);
		log.info("message: " + message + " sent to " + destination);
	}
	
}
