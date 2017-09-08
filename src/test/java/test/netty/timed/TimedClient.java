package test.netty.timed;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimedClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new TimedClientHandler1(),
                                    new TimedClientHandler2(),
                                    new TimedClientHandler3(),
                                    new TimedClientHandler4(),
                                    new TimedClientHandler5()
                            );
                        }

                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.connect(HOST, PORT).sync().channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
