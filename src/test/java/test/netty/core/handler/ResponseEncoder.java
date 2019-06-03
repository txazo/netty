package test.netty.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import test.netty.core.Constants;
import test.netty.core.Response;

import java.nio.charset.Charset;

public class ResponseEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Response response, ByteBuf out) throws Exception {
        byte[] dataBytes = response.getData().getBytes(Charset.forName("UTF-8"));

        out.writeByte(Constants.MAGIC);
        // length
        out.writeInt(8 + 4 + dataBytes.length);
        // responseId: 8
        out.writeLong(response.getResponseId());
        // data
        out.writeInt(dataBytes.length);
        out.writeBytes(dataBytes);
    }

}
