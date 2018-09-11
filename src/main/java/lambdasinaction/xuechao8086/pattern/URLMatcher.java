package lambdasinaction.xuechao8086.pattern;

/**
 * @author gumi
 * @since 2018/07/16 15:45
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URLMatcher {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
        // 空格结束
        Matcher matcher = pattern
            .matcher("随碟附送下载地址http://www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss ?sdfsyyy空格结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

        // 中文结束
        matcher = pattern
            .matcher("随碟附送下载地址http://www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss网址结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

        // 没有http://开头
        matcher = pattern
            .matcher("随碟附送下载地址www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss网址结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

        // net域名
        matcher = pattern
            .matcher("随碟附送下载地址www.xxx.net/sdfsdf.htm?aaaa=%ee%sss网址结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

        // xxx域名
        matcher = pattern
            .matcher("随碟附送下载地址www.zuidaima.xxx/sdfsdf.htm?aaaa=%ee%sss网址结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

        // yyyy域名匹配不到
        System.out.println("匹配不到yyyy域名");
        matcher = pattern
            .matcher("随碟附送下载地址www.zuidaima.yyyy/sdfsdf.html?aaaa=%ee%sss网址结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

        // 没有http://www.
        matcher = pattern
            .matcher("随碟附送下载地址zuidaima.com/sdfsdf.html?aaaa=%ee%sss网址结束");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }

    }
}