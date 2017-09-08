package test.netty.multi.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.RandomUtils;
import test.netty.multi.WorkExector;

@ChannelHandler.Sharable
public class MessageReadHandler extends ChannelInboundHandlerAdapter {

    private String name;

    public MessageReadHandler(String name) {
        this.name = name;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        try {
            long currentTimeMillis = buf.readLong();
            System.out.println(name + " Read: " + currentTimeMillis);
        } catch (Exception e) {
            buf.release();
        }

        WorkExector.getInstance().submit(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(RandomUtils.nextInt(100, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long currentTimeMillis = System.currentTimeMillis();
                System.out.println(name + " Write: " + currentTimeMillis);

                ByteBuf buf = ctx.alloc().buffer(8);
                buf.writeLong(currentTimeMillis);
                ctx.writeAndFlush(buf);
            }

        });
    }

}
