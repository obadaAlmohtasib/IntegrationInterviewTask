package com.digitinary.accounts.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig {

    // Create and configure an embedded ActiveMQ broker
    @Bean
    public BrokerService broker() throws Exception {
        BrokerService brokerService = new BrokerService();
        // Configure the broker to listen on a TCP port (61616)
        brokerService.addConnector("tcp://0.0.0.0:61616");  // Or use localhost if you prefer
        brokerService.setPersistent(false);  // Optional: Disable persistence (for testing)
        brokerService.start();
        return brokerService;
    }

    // Create an ActiveMQ connection factory for the embedded broker
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        // Connect to the embedded broker using TCP (use tcp://localhost:61616 for local connections)
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        return factory;
    }

    // Create a JMS template
    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
    
}