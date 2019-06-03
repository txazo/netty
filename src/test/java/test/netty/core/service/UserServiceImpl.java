package test.netty.core.service;

public class UserServiceImpl implements UserService {

    @Override
    public UserInfo getUser(int userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName("xxx");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

}
