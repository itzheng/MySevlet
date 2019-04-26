package org.itzheng.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

public class JsonUtils {
	/**
	 * 将json转map
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Gson gson = new Gson();
		Map<String, Object> params = gson.fromJson(jsonStr, Map.class);
		if (params == null) {
			// 避免空指针
			params = new HashMap<String, Object>();
		}
		return params;
	}

	/**
	 * 将对象转json
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	/**
	 * 将json转对象
	 * 
	 * @param json
	 * @param classType
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> classType) {
		Gson gson = new Gson();
		return gson.fromJson(json, classType);
	}

}
