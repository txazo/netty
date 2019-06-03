package test.netty.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request implements Serializable {

    private static final long serialVersionUID = 1911848522746887092L;

    private long requestId;
    private String classType;
    private String methodName;
    private Object[] args;

}
