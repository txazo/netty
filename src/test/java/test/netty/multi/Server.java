package test.netty.multi;

import io.netty.channel.ChannelHandler;

public interface Server {

    void addHandler(ChannelHandler... handlers);

    void addChildHandler(ChannelHandler... handlers);

}
