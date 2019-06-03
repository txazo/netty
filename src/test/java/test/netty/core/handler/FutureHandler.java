package test.netty.core.handler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import test.netty.core.Context;
import test.netty.core.Request;
import test.netty.core.Response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureHandler extends ChannelInAndOutBoundHandlerAdapter {

    private static final LoadingCache<Long, AtomicInteger> COUNTER_CACHE = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, AtomicInteger>() {

                @Override
                public AtomicInteger load(Long key) throws Exception {
                    return new AtomicInteger(0);
                }

            });

    private ConcurrentMap<Long, Context> futureMap = new ConcurrentHashMap<>();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Context) {
            Context context = (Context) msg;
            Request request = new Request();
            long currentMillis = System.currentTimeMillis();
            request.setRequestId(currentMillis << 8 + COUNTER_CACHE.get(currentMillis).incrementAndGet());
            request.setClassType(context.getClassType().getName());
            request.setMethodName(context.getMethodName());
            request.setArgs(context.getArgs());
            futureMap.put(request.getRequestId(), context);
            ctx.channel().writeAndFlush(request);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Response) {
            Response response = (Response) msg;
            Context context = futureMap.get(response.getResponseId());
            if (context != null) {
                context.setResponse(response.getData());
                ((FutureTask) context.getFuture()).run();
                futureMap.remove(response.getResponseId());
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }


}
