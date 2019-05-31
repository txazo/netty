package test.netty.core.service;

import test.netty.core.Request;
import test.netty.core.Response;

public interface UserService {

    Response getUser(Request request);

}
