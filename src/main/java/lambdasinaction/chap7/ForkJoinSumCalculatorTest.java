package lambdasinaction.chap7;

import org.junit.Test;

/**
 * @author xuechao
 * @since 2017/11/15 17:45
 */
public class ForkJoinSumCalculatorTest {

    @Test
    public void forkJoinSum() throws Exception {
        ForkJoinSumCalculator.forkJoinSum(100_0000);
    }

    @Test
    public void testLang() throws Exception {
        int i = 10;
        int j = i >>> 1;

        assert j == 5;
        System.out.println(i);
        System.out.println(j);
    }


}