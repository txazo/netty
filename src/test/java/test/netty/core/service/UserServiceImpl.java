package test.netty.core.service;

import test.netty.core.Request;
import test.netty.core.Response;

public class UserServiceImpl implements UserService {

    @Override
    public Response getUser(Request request) {
        Response response = new Response();
        response.setUserId(request.getUserId());
        response.setMessage(String.valueOf(System.currentTimeMillis()));
        return response;
    }

}
