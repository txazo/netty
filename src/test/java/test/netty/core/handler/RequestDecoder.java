package test.netty.core.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import test.netty.core.Constants;
import test.netty.core.Request;

public class RequestDecoder extends LengthFieldBasedFrameDecoder {

    public RequestDecoder() {
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
        if (length < 20) {
            return null;
        }

        Request request = new Request();
        long requestId = buf.readLong();
        request.setRequestId(requestId);

        int classTypeLength = buf.readInt();
        byte[] classTypeBytes = new byte[classTypeLength];
        buf.readBytes(classTypeBytes);
        request.setClassType(new String(classTypeBytes));

        int methodNameLength = buf.readInt();
        byte[] methodNameBytes = new byte[methodNameLength];
        buf.readBytes(methodNameBytes);
        request.setMethodName(new String(methodNameBytes));

        int argsLength = buf.readInt();
        byte[] argsBytes = new byte[argsLength];
        buf.readBytes(argsBytes);
        request.setArgs(JSON.parseArray(new String(argsBytes)).toArray(new Object[]{}));

        return request;
    }

}
