package test.netty.multi;

import io.netty.channel.ChannelHandler;

public interface Client {

    void addHandler(ChannelHandler... handlers);

}
