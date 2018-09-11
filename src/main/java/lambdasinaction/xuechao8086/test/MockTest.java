package lambdasinaction.xuechao8086.test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.collect.ImmutableMap;
import com.sun.deploy.net.HttpUtils;
import org.apache.commons.lang3.CharSet;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Result;
import org.mockito.Mockito;
import util.http.SendRequest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gumi
 * @since 2018/07/06 14:23
 */
public class MockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void exampleTest() throws Exception{
        stubFor(get(urlEqualTo("/my/resource"))
            .withHeader("Accept", equalTo("text/xml"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/xml")
                .withBody("<response>Some content</response>")
            )
        );

        util.http.Result result = SendRequest.sendGet("http://localhost:8089/my/resource",
            ImmutableMap.of("Content-Type", "text/xml"),
            ImmutableMap.of(),
            "utf-8"
        );
        Assert.assertNotNull(result);
    }


    @Test
    public void array_test1() {
        List<String> nameList = new ArrayList<>(10);
        List mockedNameList = Mockito.mock(List.class);
        mockedNameList.add("xuechao");
        mockedNameList.clear();

        Mockito.verify(mockedNameList).add("one");
        Mockito.verify(mockedNameList).clear();
    }

    @Test
    public void array_test2() {
        //创建mock对象，参数可以是类，也可以是接口
        List<String> list = Mockito.mock(List.class);

        //设置方法的预期返回值
        Mockito.when(list.get(0)).thenReturn("helloworld");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        Mockito.verify(list).get(0);

        //junit测试
        Assert.assertEquals("helloworld", result);
    }
}
