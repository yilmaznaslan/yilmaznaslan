# Architecture Interview Case study

- Interviewer: Great! Let's dive into the scenario. You are the lead engineer in a new team at Klarna. We're planning to
build a new web service that allows our customers to handle their online purchases, schedule their payments, and
generate payment reports. As a starting point, you'll need to design a prototype. Can you discuss the high-level
architecture for this service?

  - I'd propose a microservices-based architecture where each function like handling purchases, scheduling payments, and
  generating reports is a separate service. This would allow us to develop, deploy, and scale each service independently.

  - We could use a layered architecture where the presentation, business logic, and data access layers are clearly
  separated. This will ensure that changes in one layer have minimal impact on the others.


- Interviewer: I see. Now, thinking about this system, how would you handle the design of the database architecture? 
Given
our requirements, would you consider using a relational database, a NoSQL database, or a combination of both ?
    - Given that financial systems typically require strong consistency and reliability, I'd lean towards using a relational
database. However, for parts of the system where scalability or speed is more important, we could use a NoSQL database.

    - For the transactional data, a relational database would be a good choice due to its ACID properties. For the analytics
part where we need to generate reports, a NoSQL database like MongoDB or a data warehouse might be more suitable due to
their ability to handle large amounts of data and complex queries.

Interviewer: Alright, moving on to the deployment of this service, how would you approach scaling this service to handle
peak loads, and how would you automate this process?

Alternative Candidate Responses:

I would propose using a container orchestration system like Kubernetes, which can handle scaling based on the system's
current load. We could also use a serverless architecture, like AWS Lambda, which can scale automatically based on the
incoming request rate.

We can use a combination of vertical scaling (increasing the capacity of individual servers) and horizontal scaling (
adding more servers). For automation, we could use infrastructure as code (IaC) tools like Terraform or CloudFormation.

Interviewer: Excellent! Now, could you discuss how you'd monitor this service in a production environment? What metrics
would you focus on, and how would you set up alerts?

Alternative Candidate Responses:

We could use a combination of APM tools like New Relic or Dynatrace for application performance monitoring, and
infrastructure monitoring tools like Nagios or Zabbix. For logging, we could use ELK Stack or Splunk. In terms of
metrics, we'd focus on things like request latency, error rates, transaction volumes, and system load.

We could leverage AWS CloudWatch for monitoring if we're deploying on AWS. For distributed tracing, we could use Jaeger
or Zipkin. Metrics like CPU usage, memory usage, disk I/O, network traffic, request counts, error rates, and response
times would be essential to monitor. We can set up alerts using tools like PagerDuty or Opsgenie.