package org.itzheng.utils.db.bean.base;

import java.io.Serializable;
import java.util.List;

import org.itzheng.utils.ArrayUtils;

/**
 * 服务器请求结果的基类，所有返回几个需要继承这个类，使用泛型，后面直接添加数据库表就好了
 *
 * @param <T> 对应数据库的表名，如：class EQuoteResult extends BaseResult<EQuote>
 */
public class BaseResult<T> implements Serializable {
	public DsERPBean<T> dsERP;
	public int __result;
	public String __msg;

	public static class DsERPBean<T> implements Serializable {
		public int total;
		public List<T> rows;
	}

	/**
	 * 数据结束，结束的是时候需要进行数据检查
	 */
	public void finish() {
		if (dsERP != null) {
			dsERP.total = ArrayUtils.size(dsERP.rows);
		}
	}
}
