package com.yilmaznaslan.lab.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.Request;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JettyFundamentalsDemo {

    public static void main(String[] args) throws Exception {
        // 1. Create a Server instance on port 8080
        Server server = new Server(8080);

        // 2. Set a simple Handler to process requests
        server.setHandler(new HelloWorldHandler());

        // 3. Start the server
        System.out.println("Starting Jetty server on http://localhost:8080...");
        server.start();

        // 4. Join the server thread to keep it running
        server.join();
    }

    /**
     * A simple handler that returns "Hello Jetty!" for every request.
     */
    public static class HelloWorldHandler extends AbstractHandler {
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
                throws IOException, ServletException {
            
            // Set the content type
            response.setContentType("text/plain;charset=utf-8");
            
            // Set status
            response.setStatus(HttpServletResponse.SC_OK);

            // Write the response body
            String greeting = "Hello Jetty Fundamentals!\n";
            greeting += "Target: " + target + "\n";
            greeting += "Request URI: " + request.getRequestURI() + "\n";
            greeting += "Protocol: " + request.getProtocol() + "\n";
            
            response.getWriter().println(greeting);

            // Mark request as handled
            baseRequest.setHandled(true);
        }
    }
}

