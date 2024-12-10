package com.digitinary.customers.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JMSConfig {

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        // Create and configure the connection factory for ActiveMQ
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//        connectionFactory.setBrokerURL("tcp://localhost:61616");
//        connectionFactory.setUserName("admin");
//        connectionFactory.setPassword("admin");
//        return connectionFactory;
//    }
	
//	@Bean
//	public DefaultMessageListenerContainer messageListenerContainer(@Autowired ConnectionFactory connectionFactory) {
//	    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
//	    container.setConnectionFactory(connectionFactory);
//	    container.setDestinationName("status-changed-queue");
//	    container.setMessageListener(messageListener);
	// Adjust the Reconnect Logic / BackOff Strategy. 
//	    container.setRecoveryInterval(5000);  // Recovery interval in milliseconds
//	    container.setMaxReconnectAttempts(10);  // Max retry attempts
//	    return container;
//	}
	
}
