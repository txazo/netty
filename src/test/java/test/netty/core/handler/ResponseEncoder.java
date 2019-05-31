package test.netty.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import test.netty.core.Constants;
import test.netty.core.Response;

import java.nio.charset.Charset;

public class ResponseEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        byte[] messageBytes = msg.getMessage().getBytes(Charset.forName("UTF-8"));
        out.writeByte(Constants.HEAD);
        out.writeInt(4 + messageBytes.length);
        out.writeInt(msg.getUserId());
        out.writeBytes(messageBytes);
    }

}
