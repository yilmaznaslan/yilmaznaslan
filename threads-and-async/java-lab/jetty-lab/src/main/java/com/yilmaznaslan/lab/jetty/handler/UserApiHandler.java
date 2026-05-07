package com.yilmaznaslan.lab.jetty.handler;

import com.yilmaznaslan.lab.jetty.base.BaseRestHandler;
import com.yilmaznaslan.lab.jetty.model.GenericMessageResponse;
import com.yilmaznaslan.lab.jetty.model.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserApiHandler extends BaseRestHandler {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserResponse data = new UserResponse(List.of("John", "Doe"));
        sendJsonResponse(response, HttpServletResponse.SC_OK, data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GenericMessageResponse message = new GenericMessageResponse("User created");
        sendJsonResponse(response, HttpServletResponse.SC_CREATED, message);
    }
}

