package stream;

/**
 * @author gumi
 * @since 2018/03/13 20:33
 */
@FunctionalInterface
public interface EventConsumer {
    Event consume(Event event);
}
