package test.netty.core;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = -6561824509008461841L;

    private int userId;

    private String message;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
