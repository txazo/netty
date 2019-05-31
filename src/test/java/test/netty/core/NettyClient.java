package test.netty.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import test.netty.core.handler.RequestEncoder;
import test.netty.core.handler.ResponseDecoder;
import test.netty.core.handler.SocketChannelLoggerHandler;

public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new SocketChannelLoggerHandler())
                            .addLast(new RequestEncoder())
                            .addLast(new ResponseDecoder());
                }

            });

            final Channel channel = bootstrap.connect(HOST, PORT).sync().channel();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    channel.writeAndFlush(new Request(1111));
                }

            }).start();

            System.in.read();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
