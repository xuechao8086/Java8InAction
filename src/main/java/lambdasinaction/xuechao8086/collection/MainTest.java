package lambdasinaction.xuechao8086.collection;

import java.awt.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author gumi
 * @since 2018/09/30 14:24
 */
public class MainTest {
    public static void main(String[] args)
    {
        Map<Twins, String> hashMap = new HashMap<>(16);

        Map<Twins, String> identityMap = new IdentityHashMap<>();

        // 兄弟
        Twins brother = new Twins(Color.green);

        // 哥哥
        Twins eldBrother = new Twins(Color.green);

        hashMap.put(brother, "弟弟");
        hashMap.put(eldBrother, "哥哥");

        System.out.println(hashMap);//{com.scc.Twins@ff01010f=哥哥} 结果却只有哥哥

        identityMap.put(brother, "绿色衣服的弟弟");
        identityMap.put(eldBrother, "哥哥");

        //第二天弟弟换了一身蓝衣服
        brother.setColor(Color.BLUE);

        identityMap.put(brother, "蓝色衣服的弟弟");
        System.out.println(identityMap);//{com.scc.Twins@ff00030e=蓝色衣服的弟弟} 结果弟弟还是弟弟,只是颜色不同罢了
    }
}
