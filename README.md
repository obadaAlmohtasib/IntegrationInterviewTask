# IntegrationInterviewTask
Spring Boot Developer Task

Design Decisions:
Ideas are endless, here are some of my design decisions:
Database Design: I used H2 file database.
Event-Driven Architecture: I used ActiveMQ embedded broker (New Feature in Spring Boot 3.4) for JMS messaging.
So that my application can run standalone, and it is benificial for local development and lightweight use cases.
The JMS/Message Listener configured to retry attempt/refresh the connection as following:
FixedBackOff{interval=5000, maxAttempts=unlimited}, we can adjust it to use other BackOff strategies.

API Design: do subresourcing for actions.

-----
Security:
I setup/established authentication when hitting accounts-service.
I have secured accounts-service endpoints , 
and - for demo purposes - the way to reach/access it by using "Basic Auth" and client credential.
Of course, we can optimize it and making it more secure by using JWT, private-key and public-keys, OAuth2.

ACID adherence:
I have used "@Version" annotation.
I have Locked the "Balance" field of account, to prevent data loss across multiple transactions.

Assumptions/Enhancement (To make it more real):
- we can add "update_timestamp" field to inactivate accounts atfer X-days of no-transactions, 
  and running job (at midnight)/(at first of month) to scan and trigger an event, which can have SMS service as consumer.
- track "transfer_amount" to detect suspicious operations (fraud) -> and freezing accounts.
- Migrating data

Logging:
- we can use RollingFileAppender "Log file rolling" to defines an interval or max_size.

