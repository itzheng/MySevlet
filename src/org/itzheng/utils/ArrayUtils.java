package org.itzheng.utils;

import java.util.List;
import java.util.Map;

public class ArrayUtils {
	/**
	 * 数组是否为空
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}

	/**
	 * 字节是否为空
	 * 
	 * @param bytes
	 * @return
	 */
	public static boolean isEmpty(byte[] bytes) {
		return bytes == null || bytes.length == 0;
	}

	/**
	 * Map 是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map map) {
		return map == null || map.isEmpty();
	}

	/**
	 * List是否为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list) {
		return list == null || list.isEmpty();
	}
	/**
	 * byte大小
	 * 
	 * @param list
	 * @return
	 */
	public static int size(Object[] bytes) {
		return (bytes == null) ? 0 : bytes.length;
	}
	/**
	 * byte大小
	 * 
	 * @param list
	 * @return
	 */
	public static int size(byte[] bytes) {
		return (bytes == null) ? 0 : bytes.length;
	}

	/**
	 * Map大小
	 * 
	 * @param list
	 * @return
	 */
	public static int size(Map list) {
		return (list == null) ? 0 : list.size();
	}

	/**
	 * List大小
	 * 
	 * @param list
	 * @return
	 */
	public static int size(List list) {
		return (list == null) ? 0 : list.size();
	}
}
