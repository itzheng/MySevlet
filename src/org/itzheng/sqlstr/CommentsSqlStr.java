package org.itzheng.sqlstr;

import org.itzheng.utils.StrUtils;

/**
 * 评论相关的SQL语句,主要有博客内容评论，活动评论，俱乐部评论
 * 
 * @author WL001
 *
 */
public class CommentsSqlStr {
	/**
	 * 获取分享内容的评论
	 * 
	 * @param page
	 * @param size
	 * @param fShareID
	 * @return
	 */
	public static String getCommentsSql(final int page, final int size, String fShareID) {
		String WHERE_STRING = "fShareID='" + fShareID + "' and" + "(fPID is null or fPID=0)";
		String SQL_QUERY_FLD = "fID,fText, fDate,fUserID,\n"
				+ "(select fNickName from Users where fID=Comment.fUserId) as 'fNickName',\n"
				+ "(select fImageURL from UserImages where fUserID=Comment.fUserId and fIsAvatar=1) as'fUserPic',\n"
				+ "(select COUNT(fId) from Comment as temp where fPID=Comment.fID) as 'fCount'";
		String SORT_FLD = "fDate";
		String TABLE_NAME = "Comment";
		String PRIMARY_KEY = "fID";
		String sql = BaseSqlUtils.getPageSqlOfMe(SQL_QUERY_FLD, TABLE_NAME, PRIMARY_KEY, SORT_FLD, size, page,
				WHERE_STRING);
		return sql;

	}

	/**
	 * 获取分享内容子评论
	 * 
	 * @param page
	 * @param size
	 * @param fPID 父评论id
	 * @return
	 */
	public static String getSubCommentsSql(int page, int size, int fPID) {
		String WHERE_STRING = "fPID=" + fPID;
		String SQL_QUERY_FLD = "fID,fPID,fText,fDate ,fUserID,\n"
				+ "(select fNickName from Users where fID=Comment.fUserId) as 'fNickName',\n"
				+ "(select fNickName from Users where fID=fToUserID)as 'fToNickName'";
		String SORT_FLD = "fDate";
		String TABLE_NAME = "Comment";
		String PRIMARY_KEY = "fID";
		String sql = BaseSqlUtils.getPageSqlOfMe(SQL_QUERY_FLD, TABLE_NAME, PRIMARY_KEY, SORT_FLD, size, page,
				WHERE_STRING);
		return sql;
	}

	/**
	 * 添加评论
	 * 
	 * @param fUserID  当前用户id
	 * @param fShareID 评论的ShareID
	 * @param comment  评论的内容
	 * @return
	 */
	public static String getAddCommentSql(String fUserID, String fShareID, String comment) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO Comment (fShareID, fUserId,fText,fDate)\n" + "VALUES ('" + fShareID + "', '" + fUserID
				+ "','" + comment + "',SYSUTCDATETIME())\n");
		// 如果不是本人，还需要发送通知
		sql.append("if not exists (select * from Share where fID='" + fShareID + "' and fUserID='" + fUserID + "')");
		sql.append("\nBEGIN\n");
		sql.append("Insert into message (fId,fType,fFromUserId,fToUserId,fRefId,fOPerationId,fRead,fDate)\n"
				+ "values('" + StrUtils.getGUID() + "'," + MessageCode.Comment + ",'" + fUserID + "'," + "("
				+ ("select fUserID from Share where fID='" + fShareID + "')") + ",'" + fShareID
				+ "',(select @@IDENTITY) ,0,SYSUTCDATETIME())");
		sql.append("\nEND\n");
//		System.out.println("" + sql.toString());
		return sql.toString();
	}

	/**
	 * 回复评论
	 * 
	 * @param fPID     评论哪条ID
	 * @param fUserID  当前用户ID
	 * @param fShareID 评论的ShareID
	 * @param comment  评论的内容
	 * @return
	 */
	public static String getReplyCommentSql(int fPID, String fUserID, String fShareID, String comment) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO Comment (fPID,fToUserId,fShareID, fUserId,fText,fDate)\n" + "VALUES (" + fPID + ",'"
				+ ("select fUserID from Comment where fID=" + fPID) + "','" + fShareID + "', '" + fUserID + "','"
				+ comment + "',SYSUTCDATETIME())");
		// 如果不是本人，还需要发送通知
		sql.append("if not exists (select * from Comment where fID='" + fPID + "' and fUserID='" + fUserID + "')");
		sql.append("\nBEGIN\n");
		sql.append("Insert into message (fId,fType,fFromUserId,fToUserId,fRefId,fOPerationId,fRead,fDate)\n"
				+ "values('" + StrUtils.getGUID() + "'," + MessageCode.Comment + ",'" + fUserID + "'," + "("
				+ ("select fUserID from Comment where fID=" + fPID) + "),'" + fShareID
				+ "',(select @@IDENTITY) ,0,SYSUTCDATETIME())");
		sql.append("\nEND\n");
//		System.out.println("" + sql.toString());
		return sql.toString();
	}
}
