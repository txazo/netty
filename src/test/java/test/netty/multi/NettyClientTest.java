package test.netty.multi;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import test.netty.multi.handler.MessageReadHandler;

public class NettyClientTest {

    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);

        int size = 50;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = newClient(i, ServerPortConfig.selectPort(i), workerGroup);
        }

        waitClose(threads);

        workerGroup.shutdownGracefully();
    }

    private static Thread newClient(int i, int port, EventLoopGroup workerGroup) {
        NettyClient client = new NettyClient(HOST, port, workerGroup);
        client.addHandler(new MessageReadHandler("Client" + i));
        Thread t = new Thread(client);
        t.start();
        return t;
    }

    private static void waitClose(Thread[] threads) {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
