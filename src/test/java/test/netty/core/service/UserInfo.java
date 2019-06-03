package test.netty.core.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 4138085929843211151L;

    private int userId;

    private String userName;

}
