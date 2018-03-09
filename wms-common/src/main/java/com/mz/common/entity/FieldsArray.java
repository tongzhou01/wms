package com.mz.common.entity;

import java.lang.reflect.Field;

/**
 * 获取实体类字段数组
 *
 * @author tz
 */
public class FieldsArray {

    public static String[] getFieldsArray(Class<?> clazz) {

        Field[] fields = clazz.getDeclaredFields();
        String a = "";
        for (Field field : fields) {
            if (!"orderItemList".equals(field.getName())) {
                a += Abc.abc(field.getName()) + ",";
            }
        }
        String[] columns = a.split(",");
        return columns;
    }
}
