package test.netty.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import test.netty.core.Request;

public class RequestDecoder extends LengthFieldBasedFrameDecoder {

    public RequestDecoder() {
        super(1024, 1, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        Request request = new Request();
        buf.readByte();
        int length = buf.readInt();
        if (length == 4) {
            request.setUserId(buf.readInt());
            return request;
        }
        return null;
    }

}
