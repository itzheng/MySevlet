package org.itzheng.utils;

import java.util.UUID;

/**
 * 
 * 
 * 字符串Utils文件
 */
public class StrUtils {
	/**
	 * 将对象转成字符串，如果对象为空则返回""
	 */
	public static String toString(Object object) {
		if (object == null) {
			return "";
		}
		return object + "";
	}

	/**
	 * 去掉所有空格
	 *
	 * @param value
	 * @return
	 */
	public static String toTrimAll(Object value) {
		if (value == null || "null".equalsIgnoreCase(value.toString())) {
			return "";
		}
		String s = value.toString();
		if (s == null) {
			return "";
		}
		s = s.replace(" ", "");// 去掉英文空格
		s.replace(" ", "");// 去掉中文空格
		return s;
	}

	/**
	 * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		return isEmpty(value.toString());
	}

	/**
	 * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
	 */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
	 */
	public static boolean isEquals(String... agrs) {
		String last = null;
		for (int i = 0; i < agrs.length; i++) {
			String str = agrs[i];
			if (isEmpty(str)) {
				return false;
			}
			if (last != null && !str.equalsIgnoreCase(last)) {
				return false;
			}
			last = str;
		}
		return true;
	}

	/**
	 * 获取UUID
	 *
	 * @return
	 */
	public static String getGUID() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	/**
	 * 将对象转数字
	 * 
	 * @param object
	 * @return
	 */
	public static int toInt(Object object) {
		int num = 0;
		if (object == null) {
			return num;
		}
		try {
			num = (int) Math.ceil(toDouble(object));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 将对象转成Double
	 * 
	 * @param object
	 * @return
	 */
	public static double toDouble(Object object) {
		double num = 0;
		if (object == null) {
			return num;
		}
		try {
			num = Double.parseDouble(object.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
}
