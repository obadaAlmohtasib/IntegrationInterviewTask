# IntegrationInterviewTask
Spring Boot Developer Task

This project is built using the latest stable version of Spring Boot (3.4.0) and follows modern best practices. <br />
I have ensured that no deprecated features or APIs are used, and the application is fully compatible with the latest version of Spring Boot (Java 17+). 

Design Decisions: 
## Database Design: 
I used H2 file database.

## Event-Driven Architecture: 
I used ActiveMQ embedded broker (New Feature in Spring Boot 3.4) for JMS messaging. <br />
So that my application can run standalone, and it is benificial for local development and lightweight use cases. <br />
- The JMS/Message Listener configured to retry attempt/refresh the connection as following: <br />
FixedBackOff{interval=5000, maxAttempts=unlimited}, we can adjust it to use other BackOff strategies.

## API Design:
I implemented validation in both the API layer (using @Valid and custom annotations) and <br />
the database layer (with constraints such as @Size, @NotNull, and unique constraints on accountNumber). <br />
I implemented a global centralized exception handling.

## Microservices communication:
For communication between the **Customer** and **Account** microservices, I have used **Spring Cloud FeignClient**. <br />
Feign provides a declarative way to create REST clients, abstracts much of the complexity, and enhancing productivity.

## Security:
I have secured accounts-service endpoints , 
and - for demo purposes - the way to reach/access it by using "Basic Auth" and client credential. <br />
Of course, we can optimize it and making it more secure and scalable by using JWT, private-key and public-keys, or OAuth2.

## ACID adherence:
I have used "@Version" annotation, Locking the "Balance" field of account, to prevent data loss across multiple transactions.

## Enhancement (To make it more real):
- Add "update_timestamp" field to inactivate accounts atfer X-days of no-transactions, 
  Implement a scheduled job (running at midnight or on the first of the month) that scans and trigger an event, which can have SMS service as consumer.
- track "transfer_amount" to detect suspicious operations (fraud) -> and freezing accounts. <br />
- Migrating/Archiving data. 

## Logging:
- we can use RollingFileAppender "Log file rolling" to defines an interval or max_size.

## Profiles: 
- I using Spring Profiles to manage different environments, such as dev, prod. Each profile will have its own configuration for database connections, logging, and other environment-specific properties.


## Java 17+:
- Using "record" classes for DTO pattern - immutable data classes -. <br />
- Using "var" keyword for long class names. <br />
these what I came across while developing the task, but there are many more -> (Sealed Classes, Switch Expressions, Pattern Matching for instanceof, and more...).

