package com.yilmaznaslan.lab.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Demonstrates how to use ContextHandlers to route requests based on path,
 * and how to handle different HTTP methods (GET, POST, etc.) within a Handler.
 */
public class JettyRoutingDemo {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8082);

        // 1. Create a ContextHandler for "/api"
        ContextHandler apiContext = new ContextHandler("/api");
        apiContext.setHandler(new ApiHandler());

        // 2. Create another ContextHandler for "/status"
        ContextHandler statusContext = new ContextHandler("/status");
        statusContext.setHandler(new StatusHandler());

        // 3. Combine them using a ContextHandlerCollection
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new ContextHandler[]{apiContext, statusContext});

        server.setHandler(contexts);

        System.out.println("Starting Jetty Routing Demo on http://localhost:8082/api and http://localhost:8082/status");
        server.start();
        server.join();
    }

    /**
     * Handles different HTTP methods for the /api context
     */
    public static class ApiHandler extends AbstractHandler {
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {

            String method = request.getMethod();
            response.setContentType("application/json");

            if ("GET".equalsIgnoreCase(method)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("{\"message\": \"Received a GET request at /api\"}");
            } else if ("POST".equalsIgnoreCase(method)) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("{\"message\": \"Received a POST request at /api\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().println("{\"error\": \"Method " + method + " not supported\"}");
            }

            baseRequest.setHandled(true);
        }
    }

    /**
     * Simple status handler
     */
    public static class StatusHandler extends AbstractHandler {
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("OK - Jetty is running");
            baseRequest.setHandled(true);
        }
    }
}

