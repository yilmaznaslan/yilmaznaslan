package com.yilmaznaslan.lab.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JettyServletDemo {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);

        // ServletHandler is a simple way to register servlets
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // Register a servlet to match a specific path
        handler.addServletWithMapping(HelloServlet.class, "/hello");

        System.out.println("Starting Jetty Servlet server on http://localhost:8081/hello ...");
        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello from Jetty Servlet!</h1>");
            response.getWriter().println("<p>Context Path: " + request.getContextPath() + "</p>");
            response.getWriter().println("<p>Servlet Path: " + request.getServletPath() + "</p>");
        }
    }
}

