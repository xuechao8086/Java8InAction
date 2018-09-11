package lambdasinaction.xuechao8086.test;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

/**
 * @author gumi
 * @since 2018/06/12 10:18
 */
public class CollectionTest {

    @Test
    public void testArrayUtils() {
        String[] old_sites = {"et2", "eu13", "et15", "em14"};
        boolean contained = ArrayUtils.contains(old_sites, "et22");
        assert contained;
    }
}
