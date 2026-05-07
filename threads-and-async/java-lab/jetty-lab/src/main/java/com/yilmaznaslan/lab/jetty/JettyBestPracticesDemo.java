package com.yilmaznaslan.lab.jetty;

import com.yilmaznaslan.lab.jetty.handler.UserApiHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

/**
 * Demonstrates best practices for designing Jetty handlers using inheritance and context separation.
 */
public class JettyBestPracticesDemo {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8083);

        // Routing using ContextHandlers - Separation of concerns
        ContextHandler userContext = new ContextHandler("/api/users");
        userContext.setHandler(new UserApiHandler());

        // We can add more contexts here (e.g., /api/orders, /admin, etc.)

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new ContextHandler[]{userContext});

        server.setHandler(contexts);

        System.out.println("Starting Jetty Best Practices Demo on http://localhost:8083/api/users");
        server.start();
        server.join();
    }
}

