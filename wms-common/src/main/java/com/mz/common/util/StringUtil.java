package com.mz.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * name:字符串工具类 desc:提供字符串判断为空等操作
 *
 * @author
 */
public class StringUtil {
    public static boolean isNotNullString(String str) {
        return !isNullString(str);
    }

    public static boolean isNullString(String str) {
        return null == str || str.length() == 0 || ("null").equals(str);
    }

    /**
     * 空值检测
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || str.length() == 0;
    }

    /**
     * 非空检测
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 空格检测
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (StringUtil.isEmpty(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 无空格检测
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isNotBlank(str);
    }

    /**
     * 将整数组成的字符串转换为整数列表
     * <p>
     * 3,4,5 -> {3, 4, 5}
     *
     * @param str 字符串
     * @return
     */
    public static List<Integer> str2IntList(String str) throws Exception {
        String[] strArrays = str.split(",");
        List<Integer> intList = new ArrayList<Integer>();
        for (String strArray : strArrays) {
            intList.add(Integer.valueOf(strArray));
        }
        return intList;
    }

    /**
     * 分转换为元.
     *
     * @param fen 分
     * @return 元
     */
    public static String fromFenToYuan(final String fen) {
        String yuan = "";
        final int MULTIPLIER = 100;
        Pattern pattern = Pattern.compile("^[0-9][0-9]*{1}");
        Matcher matcher = pattern.matcher(fen);
        if (matcher.matches()) {
            yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
        } else {
            System.out.println("参数格式不正确!");
        }
        return yuan;
    }

    /**
     * 元转换为分.
     *
     * @param yuan 元
     * @return 分
     */
    public static String fromYuanToFen(final String yuan) {
        String fen = "";
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?{1}");
        Matcher matcher = pattern.matcher(yuan);
        if (matcher.matches()) {
            try {
                NumberFormat format = NumberFormat.getInstance();
                Number number = format.parse(yuan);
                double temp = number.doubleValue() * 100.0;
                // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
                format.setGroupingUsed(false);
                // 设置返回数的小数部分所允许的最大位数
                format.setMaximumFractionDigits(0);
                fen = format.format(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("参数格式不正确!");
        }
        return fen;
    }

    public static void main(String[] args) {
        String a = StringUtil.fromFenToYuan("12148");
        System.out.println(a);
    }
}