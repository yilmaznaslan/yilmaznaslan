# Common patterns for communication between services

In a microservices architecture, services need to communicate with each other to coordinate and exchange information.
The choice between **synchronous** and **asynchronous** communication depends on the specific requirements of the
application.
Let's dive deeper into these patterns and discuss some use case examples for each.

## 1) Synchronous communication:

In synchronous communication, the sender waits for the receiver's response before continuing. This type of communication
is usually based on a request-response pattern. The most common example of synchronous communication is RESTful APIs,
which use the HTTP protocol. Another example is gRPC, a high-performance, open-source framework that uses Protocol
Buffers for serialization and supports various languages.

### Use Case Examples:

- **E-commerce Order Processing**: When a customer places an order on an e-commerce platform, the order service may
  need to communicate with the inventory service to check the availability of the ordered items. This interaction
  typically requires a quick response to provide feedback to the customer, making synchronous communication, like
  RESTful APIs, a
  suitable choice.

- **Authentication and Authorization**: In a microservices-based application, an authentication service may be
  responsible for
  verifying user credentials and providing access tokens. When a user logs in or accesses a protected resource, the
  service handling the request must verify the user's identity and permissions. This process often requires synchronous
  communication with the authentication service, as the response is needed immediately to proceed.

## 2) Asynchronous communication:

In asynchronous communication, the sender does not wait for the receiver's response and can continue processing other
tasks. This type of communication is usually based on a **message-driven** or **event-driven** architecture. Examples
include

- message queues, such as Apache Kafka and RabbitMQ, and
- event-driven systems, such as AWS Lambda with Amazon EventBridge or Azure Functions with Azure Event Grid.

### Message Queues:
Message queues enable asynchronous communication between services by decoupling message producers and consumers.
Examples include RabbitMQ (AMQP-based), Apache Kafka, and AWS SQS. Producers send messages to the queue, and consumers
read and process messages independently.

#### Advantages:

Improved fault tolerance, as messages are stored in the queue until processed
Better scalability due to decoupling and support for parallel processing
Can handle various communication patterns, like publish-subscribe and point-to-point


### Event-driven Architecture:
Event-driven architecture is a pattern where services communicate by emitting and consuming events. Events represent a
state change in the system and are processed asynchronously. Examples of event-driven systems include AWS Lambda with
Amazon EventBridge or Azure Functions with Azure Event Grid.

#### Advantages:

Encourages loose coupling between services
Enables reactive and real-time processing
Supports complex event processing and event sourcing

### Use Case Examples:

- **Notifications**: In a social media platform, when a user creates a new post, other users following them should
  receive
  notifications. This process can be implemented using asynchronous communication. The post service can emit an event or
  send a message to a message broker, which will then be consumed by a notification service responsible for delivering
  notifications. As the delivery of notifications doesn't require an immediate response, asynchronous communication
  allows
  the post service to continue handling other requests without waiting.

- **Data Processing and Reporting**: An e-commerce platform may need to generate sales reports for analysis and
  decision-making. This process could involve aggregating data from multiple services, such as orders, customers, and
  products. Instead of using synchronous communication, which could lead to increased latency and performance issues, an
  asynchronous communication pattern can be employed. Services can emit events or messages when there's a change in
  their data, and a reporting service can consume these messages, update its data store, and generate reports
  asynchronously.

By considering the specific requirements of your application and the nature of the interactions between services, you
can choose the appropriate communication pattern that ensures performance, scalability, and maintainability.