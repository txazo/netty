package test.netty.multi;

public abstract class ServerPortConfig {

    private static final int[] PORTS = {9091, 9092, 9093};

    public static int[] getPorts() {
        return PORTS;
    }

    public static int selectPort(int i) {
        return PORTS[i % PORTS.length];
    }

}
