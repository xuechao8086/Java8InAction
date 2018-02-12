package lambdasinaction.xuechao8086.jmx;

import java.util.Collection;

/**
 * @author gumi
 * @since 2018/02/12 10:54
 */
public class ThreadPoolStatus implements ThreadPoolStatusMBean {
    private final TrackingThreadPool pool;

    public ThreadPoolStatus(TrackingThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public int getActiveThreads() {
        return pool.getPoolSize();
    }

    @Override
    public int getActiveTasks() {
        return pool.getActiveCount();
    }

    @Override
    public int getTotalTasks() {
        return pool.getTotalTasks();
    }

    @Override
    public int getQueuedTasks() {
        return pool.getQueue().size();
    }

    @Override
    public double getAverageTaskTime() {
        return pool.getAverageTaskTime();
    }

    @Override
    public String[] getActiveTaskNames() {
        return (String[])pool.getInProgressTasks().toArray();
    }

    @Override
    public String[] getQueuedTaskNames() {
        return (String[])pool.getQueue().toArray();
    }

    private String[] toStringArray(Collection<Runnable> collection) {
        return (String[])collection.stream()
            .map(Runnable::toString).toArray();
    }
}
