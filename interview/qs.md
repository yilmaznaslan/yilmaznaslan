Backend

Intro

* Which backend technologies do you have the most experience with?
  * If they name a language other than Java, ask about what they like/dislike about it.

Java

* What is the difference between abstract classes and interfaces in Java?
  * Abstract classes allow state (fields), and interfaces allow multiple inheritance.
* Name a few recently added Java features and when they are used.
  * Records: Provides a compact syntax for creating immutable data classes.
  * Pattern Matching: Simplifies working with instanceof and allows for more readable type checks.
  * Sealed Classes: Restrict which classes or interfaces can extend or implement a class or interface; exhaustive pattern matching
* Describe the difference between checked and unchecked exceptions.
  * Checked exceptions must be declared in the method signature or handled with a try-catch block.
  * Unchecked exceptions don't require explicit handling or declaration.
* Explain how Java handles memory management and garbage collection.
  * Java uses an automatic garbage collector to manage memory by removing unused objects.
* How do you handle multithreading in Java? What’s the difference between synchronized blocks and using Locks?
  * Synchronized blocks lock entire blocks/methods; Locks are more flexible with finer-grained control.
* Explain the concept of dependency injection and how it's implemented in Java.
  * Dependency injection enables loose coupling; commonly implemented with frameworks like Spring.

Node.js

* How does Node.js handle asynchronous operations?
  * Node.js uses an event loop and non-blocking I/O to handle asynchronous operations.
* How do you handle error handling in asynchronous functions?
  * Use try-catch with async/await or handle errors in callback functions.
* What are the trade-offs between using callback, promise, and async/await in Node.js?
  * Callbacks can lead to "callback hell"; promises are cleaner, and async/await is more readable.
* Explain the concept of middleware in Node.js.
  * Middleware is a function that receives the request and response objects. Most tasks that the middleware functions perform are:  
    * Execute any code
    * Update or modify the request and the response objects
    * Finish the request-response cycle
    * Invoke the next middleware in the stack
* What is the difference between readFile and createReadStream in Node.js?
  * Create Read Stream is a better option for reading large files, while the read file is a better option for small files.

Frontend

Intro

* Which frontend technologies do you have the most experience with?

React

* What is the virtual DOM, and how does it work in React?
  * The virtual DOM is a lightweight copy of the DOM that React uses to optimize rendering.
* What are the key differences between class components and functional components in React?
  * Class components use state and lifecycle methods; functional components with hooks are simpler and more efficient.
* Explain how hooks work, and when would you use useEffect, useState, and useMemo.
  * useState manages state; useEffect handles side effects; useMemo optimizes expensive calculations.
* How would you optimize a React application’s performance?
  * Use memoization and code-splitting to avoid unnecessary re-renders.
* What is the difference between a stateless and a stateful component in React?
  * Stateless components are simple functions that receive props and render UI, while stateful components manage their internal state, regardless of whether they're implemented as classes or functions using hooks.
* Explain the concept of Provider / Context API
  * The Context API in React, along with the Provider component, allows for efficient state management and data sharing across multiple components without explicitly passing props through each level of the component tree.

Next.js

* Describe the main features of Next.js and how they differ from CRA (Create React App).
  * Next.js supports SSR, SSG, RSC, API routes, and automatic code splitting.
* Can you explain server-side rendering (SSR) and static site generation (SSG) in Next.js?
  * SSR generates pages per request; SSG generates pages at build time.
* Can you explain React server components (RSC) in Next.js?
  * React Server Components allow developers to render components on the server, reducing client-side JavaScript and improving performance by sending only the necessary HTML to the browser.
* How would you handle API routes in a Next.js application?
  * Use Next.js’ built-in API routes for backend logic in front-end projects.

TypeScript

* What are the benefits of using TypeScript in a React project?
  * TypeScript adds type safety, reducing runtime errors and improving maintainability.
* Explain the differences between interface and type in TypeScript. When would you use one over the other?
  * Interfaces are extendable and best for objects; types are more flexible.
* How do you handle third-party libraries that don’t have TypeScript definitions?
  * Use @types packages or define custom type declarations.

CSS

* Explain the box model in CSS
  * The box model describes how elements are rendered with content, padding, borders, and margins.
* How does CSS specificity work? Give examples.
  * CSS specificity determines which styles are applied based on the selector's precision.
* Describe the concept of CSS flexbox
  * Flexbox is a layout model that allows flexible alignment and distribution of space among items in a container.
* Explain the concept of CSS Grid
  * CSS Grid is a two-dimensional layout system for creating complex, grid-based layouts.
* How do you center an element horizontally and vertically in CSS?
  * Flexbox: Use 'display: flex; justify-content: center; align-items: center;' on the parent element.
* How would you support mobile devices?
  * Media queries allow responsive design by applying different styles based on device characteristics.
  * Apply responsive design.  Responsive design uses fluid grids, flexible images, and media queries to adapt layouts to different screen sizes
* What's the difference between position: relative, absolute, fixed, and sticky?
  * Relative is positioned relative to its normal position, absolute to its nearest positioned ancestor, fixed to the viewport, and sticky toggles between relative and fixed.
* What are CSS Modules and what problem do they solve?
  * CSS Modules are a way to write CSS that's scoped locally by default, solving the problem of global namespace conflicts in stylesheets by automatically creating unique class names when compiled, thus enabling more maintainable and modular CSS in large-scale web applications.

Testing

* What are the main types of frontend testing?
  * The main types of frontend testing include 
    * static testing (like linting and type checking)
    * unit testing (testing individual components or functions)
    * integration testing (testing interactions between components)
    * functional testing (testing specific functionalities)
    * end-to-end (e2e) testing (testing the entire application flow from start to finish in a real browser environment).

Security

* Explain how XSS works, how it can be prevented
  * Cross-site scripting occurs when an attacker injects malicious scripts into a trusted website, which then executes in the user's browser, potentially stealing data or hijacking sessions.
  * How to Prevent:
    * Sanitize Inputs: Strip out or encode HTML and JavaScript from user inputs.
    * Use Content Security Policy (CSP): Restrict what scripts can be executed on the page.
    * Escape Output: Encode characters like < and > in HTML output to prevent script execution.

Microservices Architecture

* What are the key principles of microservices architecture?
  * Independently deployable services, scalability, fault isolation, and loose coupling.
* How do you manage communication between microservices?
  * Use REST, gRPC, or message brokers like Kafka.
* What strategies do you use to handle microservices’ data consistency and transactions across services?
  * Use eventual consistency, distributed transactions, or the Saga pattern.
* How would you handle service discovery and load balancing in a microservices environment?
  * Use tools like Consul, Eureka, and load balancers.
* How do you approach deploying and scaling microservices?
  * Use containers and orchestration tools like Kubernetes.

Databases

Intro

* Which database technologies do you have the most experience with?

MongoDB

* Describe the document-based model in MongoDB and the advantages and disadvantages over relational databases.
  * MongoDB stores data in flexible documents; better for unstructured data but may lack complex querying.
* What are some common MongoDB performance optimization techniques?
  * Use indexing and sharding, read from secondaries / nearest
* Explain the concept of sharding in MongoDB and its benefits.
  * Sharding distributes data across nodes, improving scalability and load balancing.
* Explain eventual consistency
  * Data will eventually become consistent across all replicas, though temporary inconsistencies may occur
  * How to achieve strong consistency
    * e.g. primary read preference + write acknowledgement from primary

Elasticsearch

* How does the Elasticsearch indexing process work?
  * Elasticsearch creates an inverted index for fast search queries.
* How do you handle data updates in Elasticsearch to ensure the index remains consistent?
  * Use document versions and partial updates for consistency.
* How does Elasticsearch handle full-text search?
  * Uses tokenization and analyzers to index text efficiently.

SQL

* What are the key differences between a relational database (SQL) and a NoSQL database?
  * SQL has strict schemas and ACID compliance; NoSQL is flexible and scales horizontally.
* Can you explain indexing in SQL and when you would use it?
  * Indexing speeds up data retrieval and is used on frequently queried columns.

General

* Explain optimistic locking
  * Assumes no conflicts and allows updates without locking. It checks for changes (using a version number or timestamp) before applying the update. If the data was modified, the update is rejected
* Describe ACID
  * Atomicity: Transactions are all-or-nothing; either every part completes, or none does.
  * Consistency: Transactions take the database from one valid state to another, preserving data integrity.
  * Isolation: Transactions operate independently without interfering, ensuring data accuracy.
  * Durability: Once a transaction is committed, changes are permanent, even in case of system failures.

Message Queuing (Kafka)

* Explain the concepts of topics, partitions, and offsets in Kafka.
  * Topics organize messages; partitions allow parallel processing; offsets track message positions.
* Explain Kafka’s partitioning and how it affects data distribution and performance.
  * Partitioning distributes data and improves throughput but requires careful handling for ordering.
* How do you ensure message reliability and fault tolerance in Kafka?
  * Use replication, acknowledgment settings, and consumer groups.
* How to handle unprocessable messages in Kafka?
  * Route them to a dead-letter topic for review and retries.
* What is Avro?
  * Avro is a schema-based, compact data serialization format used in Kafka for efficient storage and schema evolution.

REST APIs

* Explain idempotency in REST APIs and why it’s important.
  * Idempotency ensures that repeated requests have the same effect, preventing unintended changes.
* Describe the difference between REST and GraphQL. What are the advantages and disadvantages of each?
  * REST is resource-based; GraphQL is query-based, allowing more flexibility.
* How would you handle authentication and authorization in a RESTful API?
  * Use OAuth, JWT, or API keys for secure authentication and role-based authorization.
* How would you version an API, and what strategies would you consider?
  * Use versioning in URLs or headers; maintain backward compatibility;
* Describe rate limiting and throttling, and how you would implement them in a REST API.
  * Use API gateways or middleware to limit request rates and protect from abuse.
* How do you document an API?
  * Use tools like Swagger or OpenAPI for clear, interactive API documentation.

Cloud Computing

AWS

* Describe the services you are most familiar with in AWS and their use cases.
  * Examples: EC2 for computing, S3 for storage, RDS for databases, and Lambda for serverless functions.
* Explain the concept of auto-scaling in AWS and its benefits.
  * Auto-scaling adjusts resources based on demand, reducing costs and improving performance.
* Explain how you manage and secure sensitive data (like API keys or database credentials) in AWS.
  * Use AWS Secrets Manager or IAM roles with fine-grained permissions.

Kubernetes

* What is Kubernetes, and how does it help in managing containerized applications?
  * Kubernetes automates deployment, scaling, and management of containerized applications.
* Explain the concepts of Pods, ReplicaSets, and Deployments in Kubernetes.
  * Pods are the smallest deployable units; ReplicaSets manage pod replicas; Deployments control updates.
* How would you approach auto-scaling and load balancing within a Kubernetes cluster?
  * Use the Horizontal Pod Autoscaler and Service objects for load balancing.

Other Questions

* Coding challenge
  * What would you change, when you would have more time?
  * What do you have to do, to make it production ready?
* Is there any technology you would like to work with, but haven't had the chance yet? 
* What’s your opinion on generative AI and how much do you rely on it in your daily work?
* What are your expectations of your team?
  * Pairing, reviews, self-organized vs top-down
* How do you ensure your code is maintainable and testable?
  * Write modular, well-documented code with unit and integration tests.
* What are your thoughts on code quality, code reviews, and best practices?
  * They improve code reliability, readability, and team collaboration.
* Describe your experience with version control systems like Git.
  * Use Git for branching, versioning, and collaboration with pull requests.
* Describe a recent technical challenge you encountered and how you solved it.
  * Address specific issues by breaking down the problem, researching solutions, and implementing fixes.
* Explain the concept of continuous integration and continuous delivery (CI/CD) and the tools you’ve used for it.
  * CI/CD automates testing and deployment; tools include Jenkins, GitHub Actions, and GitLab CI.
* What is internationalization used for? Any preferences in mind regarding how to do so?

------

Culture Fit

Main agenda points

* Introduction of the candidate
* Introduction of the Interviewers (team)
* Open questions + Follow up questions ( Try not to overwhelm the candidate)
* 10 - 15 mins for questions from the candidate
  * Prepare yourself 😅
  * Examples:
    * What problems keep you up at night?
    * What’s the typical career path for this role?
    * Is there anything on my resume that’s a concern?
    * What do you enjoy about working here?



More examples of questions to ask

Values

[ ] Tell me about the most personally fulfilling project you've ever worked on. Why was it fulfilling? 
[ ] Tell me about a time where you put team's success over your own. Why did you do it? 
[ ] What's the best professional advice you ever received? Why did you pick this one? 
[ ] What are you currently working on improving about yourself

Team Collaboration

[ ] What do you think is the best approach to ensure that we make the right technical decisions between the tech team
[ ] When you disagree on a technical aspect what do you do?
[ ] When you know you're right and the team don't take into account your opinion?
[ ] How do you ensure quality in software that you build? 
[ ] What are 3 aspects that you consider important for a team to high perform?
[ ] What is your favourite process or methodology for working effectively with a team
[ ] What would you expect from the product manager / product owner in your team
[ ] Describe your biggest failure as an Engineer, and what did you learn from it.
[ ] Describe a situation from your experience when you had to trust someone in your team?

Self-awareness

[ ] What have been the biggest challenges working on a team? How have you overcome those problems?
[ ] What has been most rewarding about working in your current position?
[ ] What have you struggled with most in your current job? 
[ ] What characteristics are you looking for in a fulfilling workplace environment?