package ua.lpnu.lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TrackedExecutorService {
    private final ThreadPoolExecutor executorService;
    private final List<BankThread> submittedTasks;

    public TrackedExecutorService(int corePoolSize, int keepAliveTime) {
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
        this.executorService.setKeepAliveTime(keepAliveTime, TimeUnit.MILLISECONDS);
        this.submittedTasks = new ArrayList<>();
    }

    public void submitTask(BankThread task) {
        executorService.submit(() -> {
            synchronized (submittedTasks) {
                submittedTasks.add(task);
            }
            task.start();
        });
    }

    public void shutdown() {
       executorService.shutdownNow();
      submittedTasks.forEach(BankThread::stopThread);
       submittedTasks.clear();
    }
}