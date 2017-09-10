package test.netty.multi;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import test.netty.multi.handler.*;

public class NettyServerTest {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        int[] ports = ServerPortConfig.getPorts();
        Thread[] threads = new Thread[ports.length];
        for (int i = 0; i < ports.length; i++) {
            threads[i] = newServer(i, ports[i], bossGroup, workerGroup);
        }

        waitUtilClose(threads);

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    private static Thread newServer(int id, int port, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        NettyServer server = new NettyServer(port, bossGroup, workerGroup);
        server.addHandler(
                new ServerSocketChannelLoggerHandler()
        );
        server.addChildHandler(
                new SocketChannelLoggerHandler(),
                new MessageCodec(),
                new ConnectedHandler(id),
                new MessageReadHandler(id, true)
        );
        Thread t = new Thread(server);
        t.start();
        return t;
    }

    private static void waitUtilClose(Thread[] threads) {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
