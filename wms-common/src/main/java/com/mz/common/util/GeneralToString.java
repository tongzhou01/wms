package com.mz.common.util;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * name:java反射工具类
 * desc:根据java反射机制获取对象属性-值
 *
 * @author
 */
public class GeneralToString {
    public static String toString(Object obj) {
        StringBuffer strBuf = new StringBuffer();
        if (obj == null) {
            return null;
        }
        Class cla = obj.getClass();
        /**
         * 对于基本数据类型和String直接返回
         */
        if (cla == Integer.class || cla == Short.class || cla == Byte.class || cla == Long.class
                || cla == Double.class || cla == Float.class || cla == Boolean.class || cla == String.class
                || cla == Character.class) {
            strBuf.append(obj);
            return strBuf.toString();
        }

        /**
         * 对数组类型的处理
         */
        if (cla.isArray()) {
            strBuf.append("[");
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0) strBuf.append(",");
                Object val = Array.get(obj, i);

                if (val != null && !val.equals("")) {
                    strBuf.append(toString(val));
                }
            }
            strBuf.append("]");
            return strBuf.toString();
        }

        //获取所有属性
        Field[] fields = cla.getDeclaredFields();

        //设置所有属性方法可访问
        AccessibleObject.setAccessible(fields, true);


        strBuf.append("[");
        for (int i = 0; i < fields.length; i++) {
            Field fd = fields[i];
            strBuf.append(fd.getName() + "=");
            try {
                if (!fd.getType().isPrimitive() && fd.getType() != String.class) {
                    strBuf.append(toString(fd.get(obj)));
                } else {
                    strBuf.append(fd.get(obj));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i != fields.length - 1)
                strBuf.append(",");
        }

        strBuf.append("]");
        return strBuf.toString();
    }
}
