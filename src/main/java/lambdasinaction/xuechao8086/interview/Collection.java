package lambdasinaction.xuechao8086.interview;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author gumi
 * @since 2018/09/11 11:05
 */
@Slf4j
public class Collection {
    @Test
    public void testHashTable() {
        // Hashtable 已经过时了
        Hashtable<String, String> hashtable = new Hashtable<>(10);
        hashtable.put("a", "a");
        Assert.assertTrue(hashtable.size() > 0);
    }

    /**
     * 研究下定时任务是如何执行的，可以看看这片文章
     * http://ifeve.com/java-scheduledthreadpoolexecutor/
     */
    @Test
    public void testScheduled() {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " " + new Date() + " runnable");

        };

        Runnable runnableBak = () -> {
            System.out.println(Thread.currentThread().getName() + " " + new Date() + " runnableBak");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(runnableBak, 1, 1, TimeUnit.SECONDS);

        try {
            while (!scheduledExecutorService.awaitTermination(1, TimeUnit.HOURS)) {
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    @Test
    public void testAssert() {
        List<Integer> list = ImmutableList.of(1, 2, 3, 4);

        Set<Integer> set = list.stream()
            .peek(this::check)
            .collect(Collectors.toSet());

        Assert.assertTrue(set.size() > 0);
    }


    private void check(Integer i) {
        Assert.assertTrue(i > 0);
    }
}