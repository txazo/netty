package test.netty.core.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import test.netty.core.Constants;
import test.netty.core.Request;

import java.nio.charset.Charset;

public class RequestEncoder extends MessageToByteEncoder<Request> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Request request, ByteBuf out) throws Exception {
        byte[] classTypeBytes = request.getClassType().getBytes(Charset.forName("UTF-8"));
        byte[] methodNameBytes = request.getMethodName().getBytes(Charset.forName("UTF-8"));
        byte[] argsBytes = JSON.toJSONString(request.getArgs()).getBytes(Charset.forName("UTF-8"));

        out.writeByte(Constants.MAGIC);
        // length
        out.writeInt(8 + 4 + classTypeBytes.length + 4 + methodNameBytes.length + 4 + argsBytes.length);
        // requestId: 8
        out.writeLong(request.getRequestId());
        // classType
        out.writeInt(classTypeBytes.length);
        out.writeBytes(classTypeBytes);
        // methodName
        out.writeInt(methodNameBytes.length);
        out.writeBytes(methodNameBytes);
        // args
        out.writeInt(argsBytes.length);
        out.writeBytes(argsBytes);
    }

}
