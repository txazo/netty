package test.netty.core;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 1911848522746887092L;

    private int userId;

    public Request() {
    }

    public Request(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
