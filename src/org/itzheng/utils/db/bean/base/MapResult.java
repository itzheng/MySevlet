package org.itzheng.utils.db.bean.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个是将中间件返回的数据解析处理，但是没有使用javabean，而是使用map 获取的话，value=map.get(key);
 * 
 * @author WL001
 *
 */
public class MapResult extends BaseResult<Map<String, Object>> {
	/**
	 * list的数据
	 * 
	 * @author WL001
	 *
	 */
	public static class MapItem extends HashMap<String, Object> {
		public MapItem(String key, Object value) {
			put(key, value);
		}
	}

	/**
	 * 结果代码
	 * 
	 * @author WL001
	 *
	 */
	public static class ResultCode {
		/**
		 * 成功
		 */
		public static final int SUCCESS = 0;
		public static final int ERROR = -1;
	}
}
