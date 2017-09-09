package test.netty.multi.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import test.netty.multi.Message;
import test.netty.multi.WorkerExecutor;

@ChannelHandler.Sharable
public class MessageReadHandler extends ChannelInboundHandlerAdapter {

    private int tid;
    private boolean server;

    public MessageReadHandler(int tid, boolean server) {
        this.tid = tid;
        this.server = server;
    }

    private void printMessage(int oid, long time, boolean send) {
        System.out.println(String.format("%s: sid=%d, cid=%d, time=%s",
                send ? "Send" : "Recv",
                server ? tid : oid,
                server ? oid : tid,
                DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (!(msg instanceof Message)) {
            ctx.fireChannelRead(msg);
            return;
        }

        final Message message = (Message) msg;
        printMessage(message.getId(), message.getTime(), false);

        WorkerExecutor.getInstance().submit(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(RandomUtils.nextInt(100, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long time = System.currentTimeMillis();
                printMessage(message.getId(), time, true);

                ctx.writeAndFlush(new Message(tid, time));
            }

        });
    }

}
