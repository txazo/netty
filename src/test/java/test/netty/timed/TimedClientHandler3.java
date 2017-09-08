package test.netty.timed;

import io.netty.channel.ChannelHandlerContext;
import test.netty.timed.handler.ChannelInOutboundHandlerAdapter;

public class TimedClientHandler3 extends ChannelInOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("TimedClientHandler3 read");
        ctx.read();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("TimedClientHandler3 channelRead");
        ctx.fireChannelRead(msg);
    }

}
