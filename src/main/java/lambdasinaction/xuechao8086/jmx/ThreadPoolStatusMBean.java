package lambdasinaction.xuechao8086.jmx;

/**
 * @author gumi
 * @since 2018/02/12 10:53
 */
public interface ThreadPoolStatusMBean {
    int getActiveThreads();
    int getActiveTasks();
    int getTotalTasks();
    int getQueuedTasks();
    double getAverageTaskTime();
    String[] getActiveTaskNames();
    String[] getQueuedTaskNames();
}
