package org.itzheng.sqlstr;

import org.apache.commons.lang.StringEscapeUtils;
import org.itzheng.utils.StrUtils;

/**
 * SQL参数转换工具，防止SQL注入
 * 
 * @author WL001
 *
 */
public class BaseSqlUtils {
	/**
	 * 将SQL值进行转换处理
	 * 
	 * @param object
	 * @return
	 */
	public static String escapeSql(Object object) {
		if (object == null) {
			return "";
		}
		return StringEscapeUtils.escapeSql(object.toString());
	}
    /**
     * 分页查询
     *
     * @param selectColumn 查询字段，查询全部用*
     * @param tablename    表名，查询的表名
     * @param primarykey   要查询那张表的主键，根据这个主键去过滤
     * @param sortfldName  排序字段
     * @param pageSize     每页的大小
     * @param pageIndex    当前第几页，0，开始
     * @param strWhere     条件，不用加where，如果字符串需要两个单引号，如：QuoteNo like ''17Q%''
     * @return
     */
    public static String getPageSqlOfMe(String selectColumn, String tablename, String primarykey, String sortfldName,
                                        int pageSize, int pageIndex, String strWhere) {
        StringBuffer sb = new StringBuffer();
        sb.append("select top " + pageSize + " ");
        sb.append(selectColumn);
        sb.append(" from " + tablename + " ");
        String newWhere = strWhere.replace("''", "'");
        sb.append(" where " + newWhere + " ");
        if (!StrUtils.isEmpty(newWhere)) {
            sb.append(" and ");
        }
        sb.append("(" + primarykey + " not in (");
        sb.append("select top " + (pageSize * (pageIndex - 1)) + " " + primarykey + " from " + tablename + " ");
        if (!StrUtils.isEmpty(newWhere)) {
            sb.append(" where " + newWhere);
        }
        if (!StrUtils.isEmpty(sortfldName)) {
            sb.append(" order by " + sortfldName);
        }
        sb.append("))");
        if (!StrUtils.isEmpty(sortfldName)) {
            sb.append(" order by " + sortfldName);
        }
        return sb.toString();
    }
}
