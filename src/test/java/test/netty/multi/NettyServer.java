package test.netty.multi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer implements Server, Runnable {

    private int port;
    private ServerBootstrap bootstrap;

    public NettyServer(int port, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        this.port = port;
        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public void addHandler(final ChannelHandler... handlers) {
        bootstrap.handler(new ChannelInitializer<ServerSocketChannel>() {

            @Override
            public void initChannel(ServerSocketChannel ch) throws Exception {
                ch.pipeline().addLast(handlers);
            }

        });
    }

    @Override
    public void addChildHandler(final ChannelHandler... handlers) {
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(handlers);
            }

        });
    }

    @Override
    public void run() {
        try {
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
