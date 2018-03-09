package com.mz.common.util;

public class EncodingUtil {

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {

        String encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        return "";
    }

    /*public static void main(String[] args){
        System.out.println(getEncoding("CSS测试"));
    } */
    public static void main(String[] args) throws Exception {
        String desc = "CSS测试";
        try {
            String b = new String(desc.getBytes("UTF-8"), "GBK");
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
