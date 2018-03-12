package com.mz.common.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符大小写(驼峰)转换类<br>
 * 方法名即规则<br>
 * A：代表第一个字母<br>
 * B：代表下划线后第一个字母<br>
 * C: 代表其它字符
 *
 * @author zyt
 */
public class Abc {
    /**
     * 类名
     *
     * @param str
     * @return
     */
    public static String ABc(String str) {
        Matcher m = Pattern.compile("([\\_]?)([a-zA-Z0-9])([a-zA-Z0-9]*)").matcher(str.toLowerCase());
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group(2).toUpperCase());
            sb.append(m.group(3));
        }
        return sb.toString();
    }

    /**
     * 属性名
     *
     * @param str
     * @return
     */
    public static String aBc(String str) {
        Matcher m = Pattern.compile("([\\_]?)([a-zA-Z0-9])([a-zA-Z0-9]+)").matcher(str.toLowerCase());
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group(1).equals("_") ? m.group(2).toUpperCase() : m.group(2));
            sb.append(m.group(3));
        }
        return sb.toString();
    }

    /**
     * 逆向，将ABc或aBc字符串转为 a_bc
     */
    public static String abc(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c >= 65 && c < 90) {
                sb.append(sb.length() == 0 ? "" : "_");
                sb.append((char) (c + 32));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String... args) {
        System.out.println(abc("ABcAbCabCABc"));
        System.out.println(aBc("a_bcAbCabCABc"));
    }
}
