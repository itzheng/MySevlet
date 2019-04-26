package org.itzheng.sqlstr;

/**
 * 和用户相关的sql语句
 * 
 * @author WL001
 *
 */
public class UserSqlStr {

	/**
	 * 获取登录的SQL语句
	 * 
	 * @param fUserName
	 * @param fPassword
	 * @return
	 */
	public static String getLoginSql(String fUserName, String fPassword) {
		{// 对参数进行SQL注入处理
			fUserName = BaseSqlUtils.escapeSql(fUserName);
			fPassword = BaseSqlUtils.escapeSql(fPassword);
		}
		String sql = "select * from Users " + "where " + " (fUserName='" + fUserName + "' or (fEmail='" + fUserName
				+ "' and fStatus=1)) " + "and (fPassword='" + fPassword + "')";
		return sql;
	}
}
