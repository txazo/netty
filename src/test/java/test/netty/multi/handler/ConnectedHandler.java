package test.netty.multi.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import test.netty.multi.Message;

@ChannelHandler.Sharable
public class ConnectedHandler extends ChannelInboundHandlerAdapter {

    private int id;

    public ConnectedHandler(int id) {
        this.id = id;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new Message(id, System.currentTimeMillis()));
    }

}
