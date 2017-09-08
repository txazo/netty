package test.netty.multi;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import test.netty.multi.handler.ConnectedHandler;
import test.netty.multi.handler.MessageReadHandler;

public class NettyServerTest {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);

        int[] ports = ServerPortConfig.getPorts();
        Thread[] threads = new Thread[ports.length];
        for (int i = 0; i < ports.length; i++) {
            threads[i] = newServer(i, ports[i], bossGroup, workerGroup);
        }

        waitClose(threads);

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    private static Thread newServer(int i, int port, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        NettyServer server = new NettyServer(port, bossGroup, workerGroup);
        server.addHandler();
        server.addChildHandler(
                new ConnectedHandler(),
                new MessageReadHandler("Server" + i)
        );
        Thread t = new Thread(server);
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
