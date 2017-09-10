package test.netty.multi.handler;

import io.netty.channel.*;
import io.netty.util.internal.logging.InternalLogger;

import java.net.SocketAddress;

@ChannelHandler.Sharable
public abstract class LoggerHandler implements ChannelInboundHandler, ChannelOutboundHandler {

    protected abstract InternalLogger getLogger();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("channelRegistered");
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("channelUnregistered");
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("channelInactive");
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        getLogger().debug("channelRead");
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("channelReadComplete");
        ctx.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        getLogger().debug("userEventTriggered");
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("channelWritabilityChanged");
        ctx.fireChannelWritabilityChanged();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        getLogger().debug("exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        getLogger().debug("bind");
        ctx.bind(localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        getLogger().debug("connect");
        ctx.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        getLogger().debug("disconnect");
        ctx.disconnect(promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        getLogger().debug("close");
        ctx.close(promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        getLogger().debug("deregister");
        ctx.deregister(promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("read");
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        getLogger().debug("write");
        ctx.write(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("flush");
        ctx.flush();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        getLogger().debug("handlerRemoved");
    }

}
