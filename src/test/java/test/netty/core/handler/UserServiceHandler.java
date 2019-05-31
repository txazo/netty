package test.netty.core.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import test.netty.core.Request;
import test.netty.core.service.UserService;
import test.netty.core.service.UserServiceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    private UserService userService = new UserServiceImpl();
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Request) {
            final Request request = (Request) msg;
            final Channel channel = ctx.channel();
            executor.submit(new Runnable() {

                @Override
                public void run() {
                    channel.writeAndFlush(userService.getUser(request));
                }

            });
        }
    }

}
