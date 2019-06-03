package test.netty.core.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import test.netty.core.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NettyClientProxy<T> implements InvocationHandler {

    private final Class<T> interfaceClass;
    private final Channel channel;

    public NettyClientProxy(Class<T> interfaceClass, Channel channel) {
        this.interfaceClass = interfaceClass;
        this.channel = channel;
    }

    public T getRemoteProxy() {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Context context = new Context();
        context.setClassType(interfaceClass);
        context.setMethodName(method.getName());
        context.setArgs(args);
        context.setFuture(new FutureTask(new Callable() {

            @Override
            public Object call() throws Exception {
                return context.getResponse();
            }

        }));

        channel.writeAndFlush(context);

        String data = (String) context.getFuture().get();
        return JSON.parseObject(data, method.getReturnType());
    }

}
