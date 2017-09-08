package test.netty.multi.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class ConnectedHandler extends ChannelInboundHandlerAdapter {

    private int id;

    public ConnectedHandler(int id) {
        this.id = id;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = ctx.alloc().buffer(12);
        buf.writeInt(id);
        buf.writeLong(System.currentTimeMillis());
        ctx.writeAndFlush(buf);
    }

}
