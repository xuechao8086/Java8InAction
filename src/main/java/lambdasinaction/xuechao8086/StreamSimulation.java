package lambdasinaction.xuechao8086;

import java.util.Arrays;
import java.util.Spliterator;

/**
 * @author xuechao
 * @since 2017/11/17 15:05
 */
public class StreamSimulation {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        int[] source = {1, 2, 3, 4, 5};
        Spliterator<Integer> spliterator = Arrays.stream(source).spliterator();

    }

}
