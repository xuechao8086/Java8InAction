package lambdasinaction.xuechao8086;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * 主要是测试本书中jdk8的各种语言特性，确定是否实际使用该特性
 *
 * @author xuechao
 * @since 2017/11/14 15:16
 */
public class StreamTest {
    private String[] names = {"xuechaozhao", "gumi.zxc", "charliezhao", "xuechao8086", "zxc8086"};

    private void testFunc1() {
        Optional<Integer> result = Stream.iterate(0, n -> 2 * n)
            .limit(20)
            .filter(p -> p > 10)
            .findAny();
        result.ifPresent(v -> {
            assert v > 10;
            System.out.println("result = " + v);
        });
    }


    private void testFunc2() {
        // 问题： stream 是怎么split的？
        //       想办法详细debug下？
        Stream.iterate(0, n -> n + 1).limit(10).forEach(System.out::println);
    }

    @Test
    public void testFunc3() {
        Supplier<String> supplier = () -> {
            int index = (int)Math.round(Math.random())% names.length;
            return names[index];
        };

        Stream.generate(supplier).limit(10).forEach(System.out::println);
    }


    private void testFunc4() {
        List<String> result  = Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
            .filter(e -> e.length() > 3)
            .peek(e -> System.out.println("Filtered value: " + e))
            .map(String::toUpperCase)
            .peek(e -> System.out.println("Mapped value: " + e))
            .collect(Collectors.toList());

        System.out.println("#######################");

        result.forEach(System.out::println);
    }


    /**
     * 一个完整的例子，测试流api如何工作的
     */
    private void testFunc5() {
        class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
            @Override
            public Supplier<List<T>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<T>, T> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<List<T>> combiner() {
                return (list1, list2) -> {
                    list2.addAll(list1);
                    return list2;
                };
            }

            @Override
            public Function<List<T>, List<T>> finisher() {
                //return list -> list;
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics(){
                return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
            }
        }

        Supplier<String> supplier = () -> {
            int index = (int)(System.currentTimeMillis()%names.length);
            return names[index];
        };



        List<String> list = IntStream.range(1, 100_000).mapToObj(String::valueOf).parallel().collect(Collectors.toList());
        List<String> result = list.parallelStream().collect(new ToListCollector<>());

        assert result.size() > 0;
    }


    @Test
    public void testFunc6() {
        class WordCounter {
            private final int counter;
            private final boolean lastSpace;

            public WordCounter(int counter, boolean lastSpace) {
                this.counter = counter;
                this.lastSpace = lastSpace;
            }

            public WordCounter accumulate(Character c) {
                if(Character.isWhitespace(c)) {
                    return lastSpace? this : new WordCounter(counter, true);
                } else {
                    return lastSpace? new WordCounter(counter + 1, false) : this;
                }
            }

            public WordCounter combine(WordCounter wordCounter) {
                return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
            }

            public int getCounter() {
                return counter;
            }
        }


        class WordCountSpliterator implements Spliterator<Character> {
            private final String string;
            private int currentChar = 0;

            public WordCountSpliterator(String string) {
                this.string = string;
            }

            @Override
            public boolean tryAdvance(Consumer<? super Character> action) {
                action.accept(string.charAt(currentChar++));
                return currentChar < string.length();
            }

            @Override
            public Spliterator<Character> trySplit() {
                int currentSize = string.length() - currentChar;
                if(currentSize < 10) {
                    return null;
                }
                for(int splitPos = currentSize/2 + currentChar; splitPos < string.length(); splitPos++) {
                    if(Character.isWhitespace(string.charAt(splitPos))) {
                        Spliterator<Character> spliterator = new WordCountSpliterator(string.substring(currentChar, splitPos));
                        currentChar = splitPos;

                        return spliterator;
                    }
                }
                return null;
            }

            @Override
            public long estimateSize() {
                return string.length() - currentChar;
            }

            @Override
            public int characteristics() {
                return Spliterator.ORDERED + Spliterator.SIZED + Spliterator.NONNULL + Spliterator.IMMUTABLE;
            }
        }

        String sentence =   " Nel   mezzo del cammin  di nostra  vita " +
            "mi  ritrovai in una  selva oscura" +
            " ché la  dritta via era   smarrita ";
        Stream<Character> stream = IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        System.out.println("Found " + wordCounter.getCounter() + " words");

        Spliterator<Character> spliterator = new WordCountSpliterator(sentence);
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        WordCounter parallelWordCount = parallelStream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        System.out.println("Found " + parallelWordCount.getCounter() + " words");
    }


    @Test
    public void testFunc7() {
        String[] names = {"xuechao_zhao", "tingting_zhang"};

        Set<String> stringSet = Arrays.stream(names)
            .flatMap(p -> Arrays.stream(p.split("_")))
            .collect(Collectors.toSet());

        assert stringSet.size() == 4;
    }


    @Test
    public void testFunc8() {
        Function<Integer, Integer> f1 = i -> i*2;
        Function<Integer, Integer> f2 = f1.compose(i -> i +1);

        Integer r = f2.apply(10);
        assert  r == 22;

    }


    @Test
    public void testFunc9() {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 8, 0, 1)
            .parallelStream()
            .filter(i -> i%2 == 0)
            .forEach(System.out::println);
    }


    @Test
    public void testFunc10() {
        Map<Integer, Integer> r = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
            .filter(p ->  p > 5)
            .collect(Collectors.toMap(p -> p, p -> 2*p, (a, b) -> b));

        assert r.size() > 0;
    }

    @Test
    public void testFunc11() throws Exception{
        String filePath = "/Users/gumi/Downloads/t.txt";
        Map<String, Long> map = Files.lines(Paths.get(filePath))
            .parallel()
            .filter(l -> StringUtils.containsIgnoreCase(l, "alibaba"))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        map.forEach((k, v) -> System.out.println(v + "\t" + k));

    }


    public static void main(String[] args) {
        System.out.println("== main func begin ==");

        StreamTest st = new StreamTest();

        st.testFunc1();
        System.out.println("== next test ==");

        st.testFunc2();
        System.out.println("== next test ==");

        st.testFunc3();
        System.out.println("== next test ==");

        st.testFunc4();
        System.out.println("== next test ==");

        st.testFunc5();
        System.out.println("== next test ==");

        st.testFunc6();
        System.out.println("== next test ==");

        st.testFunc8();
        System.out.println("== next test ==");

        st.testFunc9();
        System.out.println("== next test ==");

        System.out.println("== main func ends ==");
    }

}
