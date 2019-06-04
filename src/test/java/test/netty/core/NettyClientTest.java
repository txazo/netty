package test.netty.core;

import test.netty.core.client.NettyClientProxy;
import test.netty.core.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyClientTest {

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient("127.0.0.1", 8080);
        client.start();
        NettyClientProxy<UserService> clientProxy = new NettyClientProxy(UserService.class, client.getChannel());
        UserService userService = clientProxy.getRemoteProxy();

        System.out.println(userService.getUser(1));

//        AtomicInteger counter = new AtomicInteger(1);
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        List<CompletableFuture> tasks = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            tasks.add(CompletableFuture.runAsync(() -> {
//                while (true) {
//                    System.out.println(userService.getUser(counter.getAndIncrement()));
//                }
//            }, executorService));
//        }
//
//        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[]{})).join();
    }

}
