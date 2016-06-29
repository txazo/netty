package test.netty.pojo;

import java.util.Date;

public class Time {

    private final long value;

    public Time() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public Time(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }

}
