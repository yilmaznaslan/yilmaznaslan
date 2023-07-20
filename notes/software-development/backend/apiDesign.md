# What is an API (Application Programming Interfaces)

API's are mechanisms that enable two software components to communicate with each other using a set of definitions and
protocols.

In the context of APIs, the word Application refers to any software with a distinct function. Interface can be thought
of as a contract of service between two applications. This contract defines how the two communicate with each other
using requests and responses. Their API documentation contains information on how developers are to structure those
requests and responses.

## How do APIs work?

API architecture is usually explained in terms of **client** and **server**. The application sending the request is
called the client, and the application sending the response is called the server.

## API Protocol types

There are four different ways that APIs can work depending on when and why they were created.


- SOAP API
- RPC API
- WebSocket API
- REST API

**SOAP APIs**

These APIs use Simple Object Access Protocol. Client and server exchange messages using XML. This is a less flexible API
that was more popular in the past.

**RPC APIs**

These APIs are called Remote Procedure Calls. The client completes a function (or procedure) on the server, and the
server sends the output back to the client.

**Websocket APIs**

Websocket API is another modern web API development that uses JSON objects to pass data. A WebSocket API supports
two-way communication between client apps and the server. The server can send callback messages to connected clients,
making it more efficient than REST API.

**REST APIs**

These are the most popular and flexible APIs found on the web today. The client sends requests to the server as data.
The server uses this client input to start internal functions and returns output data back to the client. Let’s look at
REST APIs in more detail below.

