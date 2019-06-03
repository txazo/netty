package test.netty.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import test.netty.core.Constants;
import test.netty.core.Response;

public class ResponseDecoder extends LengthFieldBasedFrameDecoder {

    public ResponseDecoder() {
        super(65536, 1, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        int magic = buf.readByte();
        if (magic != Constants.MAGIC) {
            return null;
        }

        int length = buf.readInt();
        if (length < 12) {
            return null;
        }

        Response response = new Response();
        long responseId = buf.readLong();
        response.setResponseId(responseId);

        int dataLength = buf.readInt();
        byte[] dataBytes = new byte[dataLength];
        buf.readBytes(dataBytes);
        response.setData((new String(dataBytes)));

        return response;
    }

}
