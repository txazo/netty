package test.netty.multi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkExector {

    private static final WorkExector INSTANCE = new WorkExector();

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    private WorkExector() {
    }

    public static WorkExector getInstance() {
        return INSTANCE;
    }

    public void submit(Runnable r) {
        executor.submit(r);
    }

}
