package test.netty.multi.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import test.netty.multi.Message;

@ChannelHandler.Sharable
public class MessageCodec extends DefaultInAndOutBoundHandler {

    private static final int MAGIC = 0xabee;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        if (buf.getUnsignedShort(0) == MAGIC) {
            buf.readShort();
            int id = buf.readInt();
            long time = buf.readLong();
            buf.release();
            ctx.fireChannelRead(new Message(id, time));
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Message) {
            Message message = (Message) msg;
            ByteBuf buf = ctx.alloc().buffer(14);
            buf.writeShort(MAGIC);
            buf.writeInt(message.getId());
            buf.writeLong(message.getTime());
            ctx.writeAndFlush(buf);
        } else {
            ctx.write(msg);
        }
    }

}
