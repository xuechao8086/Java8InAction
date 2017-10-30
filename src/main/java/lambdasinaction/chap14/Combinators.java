package lambdasinaction.chap14;

import java.util.function.Function;

public class Combinators {

    public static void main(String[] args) {
        System.out.println(repeat(3, (Integer x) -> 2 * x).apply(10));
    }

    private static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
        return x -> g.apply(f.apply(x));
    }

    private static <A> Function<A, A> repeat(int n, Function<A, A> f) {
        return n == 0 ? x -> x : compose(f, repeat(n - 1, f));
    }
}



/**
 思考执行过程如下（怎么这么废脑子）！

 n = 3
 repeat(3, f)
 compose(f, repeat(2, f))
 compose(f, compose(f, repeat(1, f)))
 compose(f, compose(f, compose(f, repeat(0, f))))
 compose(f, compose(f, compose(f, x->x)))
 compose(f, compose(f, x->x->2x))
 compose(f, x->x->2x->2x)
 x->x->2x->2x->2x

 */