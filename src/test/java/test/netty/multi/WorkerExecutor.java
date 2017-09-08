package test.netty.multi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerExecutor {

    private static final WorkerExecutor INSTANCE = new WorkerExecutor();

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    private WorkerExecutor() {
    }

    public static WorkerExecutor getInstance() {
        return INSTANCE;
    }

    public void submit(Runnable r) {
        executor.submit(r);
    }

}
