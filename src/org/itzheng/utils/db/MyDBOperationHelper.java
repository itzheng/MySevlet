package org.itzheng.utils.db;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.itzheng.utils.ArrayUtils;
import org.itzheng.utils.JsonUtils;
import org.itzheng.utils.MyHttpUtils;
import org.itzheng.utils.ServletUtils;
import org.itzheng.utils.StrUtils;
import org.itzheng.utils.db.bean.base.BaseResult.DsERPBean;
import org.itzheng.utils.db.bean.base.MapResult;
import org.itzheng.utils.db.bean.result.DateResult;

import com.google.gson.Gson;

/**
 * 操作数据库（中间件）的工具类<br/>
 * 其实是通过中间件操作数据库
 * 
 * @author WL001
 *
 */
public class MyDBOperationHelper {
	/**
	 * 服务器地址
	 */
	public static final String SERVER_ROOT = "http://192.168.4.20:8081/";
	/**
	 * 查询的api
	 */
	public static String DB_QUERY = SERVER_ROOT + "api/db_query";
	/**
	 * 执行sql语句的api
	 */
	private static String DB_EXEC = SERVER_ROOT + "api/db_exec";
	/**
	 * insert,update,delete 的api
	 */
	private static String DB_UPDATE = SERVER_ROOT + "api/db_update";

	/**
	 * 查询sql，并返回结果集
	 * 
	 * @param sql
	 * @return
	 */
	public static MapResult queryResult(String sql) {
		try {
			String json = MyHttpUtils.post(DB_QUERY, DbQuery.querySql(sql));
			return JsonUtils.fromJson(json, MapResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			MapResult mapResult = new MapResult();
			mapResult.__msg = e.toString();
			mapResult.__result = MapResult.ResultCode.ERROR;
			return mapResult;
		}

	}

	/**
	 * 发送查询语句，返回结果查询结果
	 * 
	 * @param sql
	 * @param onResult
	 */
	public static void query(String sql, OnResult onResult) {
		System.out.println("query:" + sql);
		try {
			String result = MyHttpUtils.post(DB_QUERY, DbQuery.querySql(sql));
			if (onResult != null) {
				onResult.onSuccess(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (onResult != null) {
				onResult.onError(e);
			}
		}
	}

	/**
	 * 向中间件执行语句并且向客户端返回结果
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param response
	 */
	public static void queryAndSetResponse(String sql, HttpServletResponse response) {
		query(sql, new OnResult() {
			@Override
			public void onSuccess(String result) {
				MapResult dbResult = JsonUtils.fromJson(result, MapResult.class);
				DsERPBean<Map<String, Object>> dsERP = dbResult.dsERP;
				List<Map<String, Object>> rows = dsERP.rows;
				MapResult mapResult = new MapResult();
				mapResult.__result = MapResult.ResultCode.SUCCESS;
				mapResult.dsERP = new DsERPBean<Map<String, Object>>();
				mapResult.dsERP.rows = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map : rows) {
					for (Entry<String, Object> entry : map.entrySet()) {
						mapResult.dsERP.rows.add(new MapResult.MapItem(entry.getKey(), entry.getValue()));
					}
				}
				ServletUtils.setResponse(response, JsonUtils.toJson(mapResult));
			}

			@Override
			public void onError(Exception e) {
				ServletUtils.setResponse(response, e.toString());
			}
		});
	}

	/**
	 * 其他insert,update,delete都可以通过exec来完成
	 * 
	 * @param sql
	 * @param onResult
	 */
	public static void exec(String sql, OnResult onResult) {
		try {
			String result = MyHttpUtils.post(DB_EXEC, DbQuery.querySql(sql));
			if (onResult != null) {
				onResult.onSuccess(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (onResult != null) {
				onResult.onError(e);
			}
		}
	}

	/**
	 * 向中间件执行语句并且向客户端返回结果
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param response
	 */
	public static void execAndSetResponse(String sql, HttpServletResponse response) {
		exec(sql, new OnResult() {

			@Override
			public void onSuccess(String result) {
				ServletUtils.setResponse(response, result);
			}

			@Override
			public void onError(Exception e) {
				ServletUtils.setResponse(response, e.toString());
			}
		});
	}

	/**
	 * 向中间件执行更新语句
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param onResult
	 */
	public static void insert(String tableName, String primaryKey, Map<String, Object> fieldsValues,
			OnResult onResult) {
		try {
			String result = MyHttpUtils.post(DB_UPDATE,
					DbQuery.updateSql(tableName, primaryKey, fieldsValues, "insert"));
			if (onResult != null) {
				onResult.onSuccess(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (onResult != null) {
				onResult.onError(e);
			}
		}
	}

	/**
	 * 向中间件执行更新语句并且向客户端返回结果
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param response
	 */
	public static void insertAndSetResponse(String tableName, String primaryKey, Map<String, Object> fieldsValues,
			HttpServletResponse response) {
		insert(tableName, primaryKey, fieldsValues, new OnResult() {

			@Override
			public void onSuccess(String result) {
				ServletUtils.setResponse(response, result);
			}

			@Override
			public void onError(Exception e) {
				ServletUtils.setResponse(response, e.toString());
			}
		});
	}

	/**
	 * 向中间件执行删除语句
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param onResult
	 */
	public static void delete(String tableName, String primaryKey, Map<String, Object> fieldsValues,
			OnResult onResult) {
		try {
			String result = MyHttpUtils.post(DB_UPDATE,
					DbQuery.updateSql(tableName, primaryKey, fieldsValues, "delete"));
			if (onResult != null) {
				onResult.onSuccess(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (onResult != null) {
				onResult.onError(e);
			}
		}
	}

	/**
	 * 向中间件执行删除语句并且向客户端返回结果
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param response
	 */
	public static void deleteAndSetResponse(String tableName, String primaryKey, Map<String, Object> fieldsValues,
			HttpServletResponse response) {
		delete(tableName, primaryKey, fieldsValues, new OnResult() {

			@Override
			public void onSuccess(String result) {
				ServletUtils.setResponse(response, result);
			}

			@Override
			public void onError(Exception e) {
				ServletUtils.setResponse(response, e.toString());
			}
		});
	}

	/**
	 * 向中间件执行更新语句
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param onResult
	 */
	public static void update(String tableName, String primaryKey, Map<String, Object> fieldsValues,
			OnResult onResult) {
		try {
			String result = MyHttpUtils.post(DB_UPDATE,
					DbQuery.updateSql(tableName, primaryKey, fieldsValues, "update"));
			if (onResult != null) {
				onResult.onSuccess(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (onResult != null) {
				onResult.onError(e);
			}
		}
	}

	/**
	 * 向中间件执行更新语句并且向客户端返回结果
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param fieldsValues
	 * @param response
	 */
	public static void updateAndSetResponse(String tableName, String primaryKey, Map<String, Object> fieldsValues,
			HttpServletResponse response) {
		update(tableName, primaryKey, fieldsValues, new OnResult() {

			@Override
			public void onSuccess(String result) {
				ServletUtils.setResponse(response, result);
			}

			@Override
			public void onError(Exception e) {
				ServletUtils.setResponse(response, e.toString());
			}
		});
	}

	/**
	 * 封装中间件请求的bean
	 */
	private static class DbQuery implements Serializable {
		private static final String DATA_SOURCE = "iSUP";
		private static final String LIST_ID = "dsERP";

		public static String querySql(String sql) {
			DbQuery dbQuery = new DbQuery();
			dbQuery.datasource = DATA_SOURCE;
			dbQuery.response = "json";
			dbQuery.list = new ArrayList<>();
			DbQuery.ListBean listBean = new DbQuery.ListBean();
			listBean.id = LIST_ID;
			listBean.sql = sql;
			dbQuery.list.add(listBean);
			return JsonUtils.toJson(dbQuery);
		}

		/**
		 * 更新语句sql
		 * 
		 * @param tableName    要更新的表名
		 * @param primaryKey   主键名称
		 * @param fieldsValues 字段和值，需要包含主键
		 * @param type         操作类型:insert,update,delete
		 * @return 更新的SQL语句
		 */
		public static String updateSql(String tableName, String primaryKey, Map<String, Object> fieldsValues,
				String type) {
			/*
			 * { "datasource": "iSUP", "list": [ { "id": "dsERP", "__updateTable": "Users",
			 * "__keyFields": "fId", "list": [ { "fId": { "value":
			 * "039D0C44474F471DAF4753B5B06000F8" }, "fDate": { "newValue":
			 * "2019-01-01 00:00:00.000" }, "__type": "update" } ] } ] }
			 */
			DbQuery dbQuery = new DbQuery();
			dbQuery.datasource = DATA_SOURCE;
			dbQuery.response = "json";
			dbQuery.list = new ArrayList<>();
			DbQuery.ListBean listBean = new DbQuery.ListBean();
			listBean.id = LIST_ID;
			listBean.__updateTable = tableName;
			listBean.__keyFields = primaryKey;
			if (!ArrayUtils.isEmpty(fieldsValues)) {
				// 内容不为空则进行操作
				listBean.list = new ArrayList<>();
				FieldMap fieldMap = new FieldMap();
				for (Entry<String, Object> entry : fieldsValues.entrySet()) {
					// 设置值
					// 判断值是否是日期格式，如果是日期格式需要转成服务器匹配类型
					Object value = entry.getValue();
					if (value != null && !StrUtils.isEmpty(value.toString())
							&& DbTimeUtils.valiDateTime(value.toString())) {
						value = DbTimeUtils.toDateTime(value.toString());
					}
					fieldMap.put(entry.getKey(), entry.getKey().equalsIgnoreCase(primaryKey), value);

				}
				/**
				 * 操作类型:insert,update,delete
				 */
				fieldMap.put("__type", type);
				listBean.list.add(fieldMap);
			}
			dbQuery.list.add(listBean);
			return JsonUtils.toJson(dbQuery);
		}

		public static class FieldMap extends HashMap {

			public FieldMap() {
			}

			public FieldMap put(String field, Boolean isPrimary, Object value) {
				HashMap<String, Object> fieldValue = new HashMap<String, Object>();
				fieldValue.put(isPrimary ? "value" : "newValue", value);
				this.put(field, fieldValue);
				return this;
			}
		}

		/**
		 * datasource : dsERP response : json list : [{"id":"dsERP","sql":"select * from
		 * dbo.goods"}]
		 */
		public String datasource;
		public String response;
		public List<ListBean> list;

		public static class ListBean implements Serializable {

			/**
			 * id : dsERP 请求id是什么，返回结果的id就是什么 sql : select * from dbo.goods
			 */

			public String id;
			/**
			 * 查询和exec语句中的SQL
			 */
			public String sql;
			/**
			 * 需要操作的表,如Users
			 */
			public String __updateTable;
			/**
			 * 操作表的主键名称,如：fID
			 */
			public String __keyFields;
			public ArrayList list;
		}
	}

	/**
	 * 获取服务器时间，添加到fDate字段
	 * 
	 * @param fieldsValues
	 */
	public static void addFDate(Map<String, Object> fieldsValues) {
		String sql = "select GETDATE() as 'fDate'";
		try {
			String result = MyHttpUtils.post(DB_QUERY, DbQuery.querySql(sql));
			DateResult dateResult = JsonUtils.fromJson(result, DateResult.class);
			String fDate = dateResult.dsERP.rows.get(0).fDate;
			fieldsValues.put("fDate", fDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行查询语句获取数量，必须是fCount字段
	 * 
	 * @return
	 */
	public static int queryCount(String sql) {
		MapResult mapResult = queryResult(sql);
		try {
			return StrUtils.toInt(mapResult.dsERP.rows.get(0).get("fCount"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

	/**
	 * 查询map结果，其实就是直接处理结果
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> queryMaps(String sql) {
		MapResult mapResult = queryResult(sql);
		return processMapResult(mapResult);
	}

	public static List<Map<String,Object>> processMapResult(MapResult mapResult) {
		try {
			return mapResult.dsERP.rows;
		} catch (Exception e) {
			return null;
		}

	}
}
