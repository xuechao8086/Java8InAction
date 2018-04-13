package stream;

/**
 * @author gumi
 * @since 2018/03/13 20:32
 */
public interface EventStream {
    void consume(EventConsumer consumer);
}
