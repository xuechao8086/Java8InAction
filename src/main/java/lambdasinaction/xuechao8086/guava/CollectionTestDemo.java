package lambdasinaction.xuechao8086.guava;

import com.google.common.collect.*;
import lambdasinaction.xuechao8086.guava.model.Employee;
import lambdasinaction.xuechao8086.guava.model.Goods;
import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.functors.IfClosure;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.apache.commons.collections4.list.PredicatedList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author gumi
 * @since 2018/04/13 10:26
 */
public class CollectionTestDemo {

    /**
     * guava multimap
     */
    @Test
    public void testMultimap() {
        Map<Integer, List<Integer>> r = IntStream.range(1, 10)
            .boxed()
            .map(i -> ImmutablePair.of(i, IntStream.range(0, i).boxed().collect(Collectors.toList())))
            .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

        assert r.size() > 0;

        Multimap<Integer, Integer> multimap = IntStream.range(1, 10)
            .boxed()
            .map(i ->
                IntStream.range(0, i).boxed().map(o -> ImmutablePair.of(i, o)).collect(Collectors.toList())
            )
            .flatMap(Collection::stream)
            .collect(Multimaps.toMultimap(Pair::getLeft, Pair::getRight, ArrayListMultimap::create));
        assert multimap.size() > 0;

        Collection<Integer> value = multimap.get(9);
        assert value.size() > 0;
    }

    /**
     * guava bimap
     */
    @Test
    public void testBiMap() {
        BiMap<Integer, Integer> biMap = IntStream.range(1, 10)
            .boxed()
            .map(i -> ImmutablePair.of(i, i*10))
            .collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (a, b) ->b, HashBiMap::create));

        assert biMap.size() > 0;

        Map<Integer, Integer> reverseBiMap = biMap.inverse();
        assert reverseBiMap.size() > 0;
    }

    /**
     * guava table
     */
    @Test
    public void testTable() {
        Table<String, String, String> infoGraph = HashBasedTable.create();

        infoGraph.put("xuechao", "zhao", "man");
        infoGraph.put("tingting", "zhang", "woman");
        infoGraph.put("rongrong", "zhao", "child");

        Map<String, String> rowMap = infoGraph.row("tingting");
        assert rowMap.size() > 0;

        Map<String, String> colMap = infoGraph.column("zhao");
        assert colMap.size() > 0;
    }

    /**
     * apache common bag
     * 注意bagd的使用场景
     */
    @Test
    public void testBag() {
        Bag<Integer> bag = IntStream.range(1, 10)
            .flatMap(i -> IntStream.of(0, i))
            .boxed()
            .collect(HashBag::new, HashBag::add, HashBag::addAll);

        assert bag.size() > 0;
    }

    /**
     * Apache Commons Collections基本操作（Predicate、Transformat、Closure等）
     * https://blog.csdn.net/scgaliguodong123_/article/details/45874503
     *
     */
    @Test
    public void testApachePredicate() {
        Predicate<Integer> uniquePre = UniquePredicate.uniquePredicate();

        // 该list可以确保元素唯一, 插入已经存在的元素则会throw exception
        // 测试 apache collections predict发挥的作用
        PredicatedList<Integer> list = PredicatedList.predicatedList(
            new ArrayList<>(10),
            uniquePre);

        list.add(10);
        list.add(200);
        assert list.size() > 0;

        Predicate<String> stringLengthPre = new Predicate<String>() {
            @Override
            public boolean evaluate(String object) {
                return StringUtils.isNotBlank(object) && object.length() < 10;
            }
        };

        PredicatedList<String> stringList = PredicatedList.predicatedList(new ArrayList<>(10),
            stringLengthPre);
        stringList.add("xuechao");
        stringList.add("xuechaooooooooooooooooo");

    }

    /**
     * 目前看，jdk8后 Transformer用处不大
     */
    @Test
    public void testTransform() {
        Transformer<Long, String> transformer = input -> String.valueOf(input) + "--" + String.valueOf(input);

        Collection<String> result = CollectionUtils.collect(ImmutableList.of(100L, 200L), transformer);
        assert result.size() > 0;
    }

    /**
     * 目前看，jdk8后 Closure用处不大
     */
    @Test
    public void testClosure() {
        List<Employee> employeeList = ImmutableList.of(
            new Employee("mark",20000),
            new Employee("json",10000),
            new Employee("Ivy",5000)
        );

        //业务功能
        Closure<Employee> cols = emp -> emp.setSalary(emp.getSalary() * 1.2);
        IterableUtils.forEach(employeeList, cols);


        List<Goods> goodsList = ImmutableList.of(
            new Goods("android视频",120,true),
            new Goods("javaee视频",80,false),
            new Goods("hadoop视频",150,false)
        );

        Closure<Goods> subtract = goods -> {
            if(goods.getPrice() >= 100) {
                goods.setPrice(goods.getPrice() - 20);
            }
        };

        Closure<Goods> discount = goods -> {
            if(goods.isDiscount()) {
                goods.setPrice(goods.getPrice() * 0.9);
            }
        };

        Predicate<Goods> pre = Goods::isDiscount;
        Closure<Goods> ifClo = IfClosure.ifClosure(pre,discount,subtract);
        IterableUtils.forEach(goodsList, ifClo);
    }

}
