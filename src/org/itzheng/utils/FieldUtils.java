package org.itzheng.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WL001 on 2017/5/17.
 */

public class FieldUtils {
    /**
     * 根据属性名获取属性值
     */
//    public static Object getFieldValueByName(String fieldName, Object o) {
//        String firstLetter = fieldName.substring(0, 1).toUpperCase();
//        String getter = "get" + firstLetter + fieldName.substring(1);
//        Method method = null;
//        Object value = null;
//        try {
//            method = o.getClass().getMethod(getter, new Class[]{});
//            value = method.invoke(o, new Object[]{});
//        } catch (NoSuchMethodException e) {
////            e.printStackTrace();
//        } catch (InvocationTargetException e) {
////            e.printStackTrace();
//        } catch (IllegalAccessException e) {
////            e.printStackTrace();
//        }
//        return value;
//    }

    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        // 得到类对象
        Class userCla = (Class) o.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            if (!fieldName.equalsIgnoreCase(f.getName())) {
                //如果名字不同则跳过
                continue;
            }
            f.setAccessible(true); // 设置些属性是可以访问的
//            Object val = new Object();
            try {
                return f.get(o);
                // 得到此属性的值
//                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * @param fieldName 字段名
     * @param o         对象
     * @param value     值
     */
    public static void setFieldValueByName(String fieldName, Object o, Object value) {
        // 得到类对象
        Class userCla = (Class) o.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            if (!fieldName.equalsIgnoreCase(f.getName())) {
                //如果名字不同则跳过
                continue;
            }
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                f.set(o, value);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 获取属性名数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    public static Field[] getDeclaredFields(Object o) {
        return o.getClass().getDeclaredFields();
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    public static List getFiledsInfo(Object o) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        List list = new ArrayList();
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     */
    public static Object[] getFiledValues(Object o) throws Exception {
        String[] fieldNames = getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }
}
