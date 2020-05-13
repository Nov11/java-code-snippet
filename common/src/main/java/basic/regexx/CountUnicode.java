package basic.regexx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountUnicode {
    private static final Logger logger = LoggerFactory.getLogger(CountUnicode.class);
    public static void main(String[] args) {
        String s = "功夫";
        System.out.println(s.length());
        System.out.println(s.codePointCount(0, s.length()));
        String s1 = "\u760c\u0444\u03b3\u03b5\ud800\udf45";
        System.out.println(s1.length());
        System.out.println(s1.codePointCount(0, s1.length()));
        String s3 = "abcdef|哈哈";
        String[] s4 = s3.split("\\|");
        logger.info("{}", s4);
        String s5 = "12345";
        String[] s55 = s5.split("\\|");
        logger.info("s55 : {}", s55);
    }
}
