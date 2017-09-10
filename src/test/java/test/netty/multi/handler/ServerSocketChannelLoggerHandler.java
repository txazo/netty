package test.netty.multi.handler;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class ServerSocketChannelLoggerHandler extends LoggerHandler {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(ServerSocketChannelLoggerHandler.class);

    @Override
    protected InternalLogger getLogger() {
        return LOGGER;
    }

}
