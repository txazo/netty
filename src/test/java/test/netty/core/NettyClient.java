package test.netty.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import test.netty.core.handler.FutureHandler;
import test.netty.core.handler.RequestEncoder;
import test.netty.core.handler.ResponseDecoder;
import test.netty.core.handler.SocketChannelLoggerHandler;

public class NettyClient {

    private String host;
    private int port;
    private Channel channel;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
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
                        .addLast(new ResponseDecoder())
                        .addLast(new FutureHandler());
            }

        });

        this.channel = bootstrap.connect(host, port).sync().channel();
    }

    public Channel getChannel() {
        return channel;
    }

}
