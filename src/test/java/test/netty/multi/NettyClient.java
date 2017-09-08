package test.netty.multi;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient implements Client, Runnable {

    private String host;
    private int port;
    private Bootstrap bootstrap;

    public NettyClient(String host, int port, EventLoopGroup workerGroup) {
        this.host = host;
        this.port = port;
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public void addHandler(final ChannelHandler... handlers) {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(handlers);
            }

        });
    }

    @Override
    public void run() {
        try {
            bootstrap.connect(host, port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
