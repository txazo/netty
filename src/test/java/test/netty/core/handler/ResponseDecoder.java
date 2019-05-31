package test.netty.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import test.netty.core.Response;

public class ResponseDecoder extends LengthFieldBasedFrameDecoder {

    public ResponseDecoder() {
        super(1024, 1, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        Response response = new Response();
        buf.readByte();
        int length = buf.readInt();
        if (length >= 4) {
            response.setUserId(buf.readInt());
            if (buf.readableBytes() > 0) {
                byte[] messageBytes = new byte[length - 4];
                buf.readBytes(messageBytes);
                response.setMessage(new String(messageBytes));
            }
            return response;
        }
        return null;
    }

}
