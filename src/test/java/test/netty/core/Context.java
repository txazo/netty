package test.netty.core;

import lombok.Data;

import java.util.concurrent.Future;

@Data
public class Context {

    private Class<?> classType;
    private String methodName;
    private Object[] args;
    private Object response;
    private Future future;

}
