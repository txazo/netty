package test.netty.multi;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import test.netty.multi.handler.DefaultInAndOutBoundHandler;
import test.netty.multi.handler.MessageCodec;
import test.netty.multi.handler.MessageReadHandler;

public class NettyClientTest {

    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        int size = 5;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = newClient(i, ServerPortConfig.selectPort(i), workerGroup);
        }

        waitClose(threads);

        workerGroup.shutdownGracefully();
    }

    private static Thread newClient(int id, int port, EventLoopGroup workerGroup) {
        NettyClient client = new NettyClient(HOST, port, workerGroup);
        client.addHandler(
                new DefaultInAndOutBoundHandler(),
                new MessageCodec(),
                new MessageReadHandler(id, false)
        );
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
