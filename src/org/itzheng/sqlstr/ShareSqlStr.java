package org.itzheng.sqlstr;

public class ShareSqlStr {
	private static final String TABLE_NAME = "Share";
	private static final String PRIMARY_KEY = "fID";

	/**
	 * 关注
	 * 
	 * @param fUserId
	 * @param page
	 * @param size
	 */
	public static String getShareByFollowSql(String fUserId, final int page, final int size) {
		String WHERE_STRING = "fType=0 and fUserID in(select fByUserID from Relation where Relation.fUserID='" + fUserId
				+ "' and fType=0)\n" +
				// "--0所有人可见，自己可见，刚好就是自己的，指定好友可见，刚好自己就是指定的那个好友，指定好友部可见，自己不是不可见的好友成员\n" +
				"and (fAuthority=0 or (fAuthority=1 and Share.fUserID='" + fUserId + "')\n"
				+ "or(fAuthority=2 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel>0 and fUserID='"
				+ fUserId + "')>0)\n" +
				// "--不可见时，自己不在列表中\n" +
				"or (fAuthority=3 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel=0 and fUserID='"
				+ fUserId + "')=0))";
		String SQL_QUERY_FLD = "fID,fType,fTitle,fText,fDate ,fUserID,DATEDIFF(Minute, fDate, SYSUTCDATETIME()) as fDiffDate,\n"
				+ "(select fID from Club where fCreatorID=fUserID) as 'fClubId',1 as 'fIsFollow',\n"
				+ "(select COUNT(fID) from Favorite where fUserID='" + fUserId
				+ "' and fShareID=Share.fID) as 'fIsFavorite',\n"
				+ "(select fNickName from Users where Users.fID=Share.fUserID) as fNickName,\n"
				+ "(select top 1 fImageURL from UserImages where UserImages.fUserID=Share.fUserID and fIsAvatar=1) as fUserPic,\n"
				+ "(select COUNT(fID)from ShareVisitor where fShareID=Share.fID)as 'fVisitCount',\n"
				+ "(select COUNT(fID)from Comment where fShareID=Share.fID)as 'fCommentsCount',\n"
				+ "(select fLatitude from Address where Address.fID= fPostAddressID) as 'fPostLatitude',\n"
				+ "(select fLongitude from Address where Address.fID= fPostAddressID) as 'fPostLongitude',\n"
				+ "(select fAddress from Address where Address.fID= fPostAddressID) as 'fPostAddress',\n"
				+ "(select fCity from Address where Address.fID= fPostAddressID) as 'fPostCity',\n"
				+ "(SELECT STUFF((SELECT ';' + fImageURL FROM ShareImages where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fImages',\n"
				+ "(SELECT STUFF((SELECT ';' + fVideoURL FROM ShareVideos where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fVideos'";
		String SORT_FLD = "fDate DESC";
		String sql = BaseSqlUtils.getPageSqlOfMe(SQL_QUERY_FLD, TABLE_NAME, PRIMARY_KEY, SORT_FLD, size, page,
				WHERE_STRING);
		return sql;
	}

	/**
	 * 推荐
	 * 
	 * @param fUserId
	 * @param page
	 * @param size
	 * @return
	 */
	public static String getShareByForyouSql(String fUserId, int page, int size) {
		String WHERE_STRING = "fType=0 and fUserID not in(select fByUserID from Relation where fUserID='" + fUserId
				+ "')\n" +
//              "--0所有人可见，自己可见，刚好就是自己的，指定好友可见，刚好自己就是指定的那个好友，指定好友部可见，自己不是不可见的好友成员\n" +
				"and (fAuthority=0 or (fAuthority=1 and Share.fUserID='" + fUserId + "')\n"
				+ "or(fAuthority=2 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel>0 and fUserID='"
				+ fUserId + "')>0)\n" +
//              "--不可见时，自己不在列表中\n" +
				"or (fAuthority=3 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel=0 and fUserID='"
				+ fUserId + "')=0))";
		String SQL_QUERY_FLD = "fID,fType,fTitle,fText,fDate ,fUserID,DATEDIFF(Minute, fDate, SYSUTCDATETIME()) as fDiffDate,\n"
				+ "(select fID from Club where fCreatorID=fUserID) as 'fClubId',0 as 'fIsFollow',\n"
				+ "(select COUNT(fID) from Favorite where fUserID='" + fUserId
				+ "' and fShareID=Share.fID) as 'fIsFavorite',\n"
				+ "(select fNickName from Users where Users.fID=Share.fUserID) as fNickName,\n"
				+ "(select top 1 fImageURL from UserImages where UserImages.fUserID=Share.fUserID and fIsAvatar=1) as fUserPic,\n"
				+ "(select COUNT(fID)from ShareVisitor where fShareID=Share.fID)as 'fVisitCount',\n"
				+ "(select COUNT(fID)from Comment where fShareID=Share.fID)as 'fCommentsCount',\n"
				+ "(select fLatitude from Address where Address.fID= fPostAddressID) as 'fPostLatitude',\n"
				+ "(select fLongitude from Address where Address.fID= fPostAddressID) as 'fPostLongitude',\n"
				+ "(select fAddress from Address where Address.fID= fPostAddressID) as 'fPostAddress',\n"
				+ "(select fCity from Address where Address.fID= fPostAddressID) as 'fPostCity',\n"
				+ "(SELECT STUFF((SELECT ';' + fImageURL FROM ShareImages where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fImages',\n"
				+ "(SELECT STUFF((SELECT ';' + fVideoURL FROM ShareVideos where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fVideos'";
		String SORT_FLD = "fDate DESC";
		String sql = BaseSqlUtils.getPageSqlOfMe(SQL_QUERY_FLD, TABLE_NAME, PRIMARY_KEY, SORT_FLD, size, page,
				WHERE_STRING);
		return sql;
	}

	/**
	 * 附近的人
	 * 
	 * @param fUserId
	 * @param page
	 * @param size
	 * @param latitude  纬度
	 * @param longitude 经度
	 * @return
	 */
	public static String getShareByNearBySql(String fUserId, int page, int size, Double latitude, Double longitude) {
		String sql = "select top " + size
				+ " fID,fType,fTitle,fText,fDate ,fUserID,DATEDIFF(Minute, fDate, SYSUTCDATETIME()) as fDiffDate,\n"
				+ "  (select fID from Club where fCreatorID=fUserID) as 'fClubId',\n"
				+ "  (select COUNT (*) from Relation where Relation.fUserID='" + fUserId
				+ "' and Relation.fByUserID=Share.fUserID and Relation.fType=0) as 'fIsFollow',\n"
				+ "  (select COUNT(fID) from Favorite where fUserID='" + fUserId
				+ "' and fShareID=Share.fID) as 'fIsFavorite',\n"
				+ "  (select fNickName from Users where Users.fID=Share.fUserID) as fNickName,\n"
				+ "  (select top 1 fImageURL from UserImages where UserImages.fUserID=Share.fUserID and fIsAvatar=1) as fUserPic,\n"
				+ "  (select COUNT(fID)from ShareVisitor where fShareID=Share.fID)as 'fVisitCount',\n"
				+ "  (select COUNT(fID)from Comment where fShareID=Share.fID)as 'fCommentsCount',\n"
				+ "  (select fLatitude from Address where Address.fID= fPostAddressID) as 'fPostLatitude',\n"
				+ "  (select fLongitude from Address where Address.fID= fPostAddressID) as 'fPostLongitude',\n"
				+ "  (select ACOS(SIN((" + latitude + " * 3.1415) / 180) * SIN((fLatitude * 3.1415) / 180 ) +\n"
				+ "  COS((" + latitude + "* 3.1415) / 180 ) * COS((fLatitude * 3.1415) / 180 ) *COS((" + longitude
				+ "* 3.1415) / 180 -\n"
				+ "  (fLongitude * 3.1415) / 180 ) ) * 6380 from Address where Address.fID=fPostAddressID ) as 'fDistance',\n"
				+ "  (select fAddress from Address where Address.fID= fPostAddressID) as 'fPostAddress',\n"
				+ "  (select fCity from Address where Address.fID= fPostAddressID) as 'fPostCity',\n"
				+ "  (SELECT STUFF((SELECT ';' + fImageURL FROM ShareImages where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fImages',\n"
				+ "  (SELECT STUFF((SELECT ';' + fVideoURL FROM ShareVideos where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fVideos'\n"
				+ "  from Share\n" + "  where fType=0 and fUserID not in(select fByUserID from Relation where fUserID='"
				+ fUserId + "' and fType=1)\n" + "  and (fAuthority=0 or (fAuthority=1 and Share.fUserID='" + fUserId
				+ "')\n"
				+ "  or(fAuthority=2 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel>0 and fUserID='"
				+ fUserId + "')>0)\n"
				+ "  or (fAuthority=3 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel=0 and fUserID='"
				+ fUserId + "')=0))\n" + "  and (fID not in (\n" + "   select fid from(\n" + "  select top "
				+ ((page - 1) * size) + " fID,(select ACOS(SIN((" + latitude
				+ " * 3.1415) / 180) * SIN((fLatitude * 3.1415) / 180 ) +\n" + "  COS((" + latitude
				+ "* 3.1415) / 180 ) * COS((fLatitude * 3.1415) / 180 ) *COS((" + longitude + "* 3.1415) / 180 -\n"
				+ "  (fLongitude * 3.1415) / 180 ) ) * 6380 from Address where Address.fID=fPostAddressID ) as 'fDistance'\n"
				+ "   from Share  where fType=0 and fUserID not in(select fByUserID from Relation where fUserID='"
				+ fUserId + "' and fType=1)\n" + "  and (fAuthority=0 or (fAuthority=1 and Share.fUserID='" + fUserId
				+ "')\n"
				+ "  or(fAuthority=2 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel>0 and fUserID='"
				+ fUserId + "')>0)\n"
				+ "  or (fAuthority=3 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel=0 and fUserID='"
				+ fUserId + "')=0))\n" + "  order by fDistance ASC,fDate DESC)as temp))\n"
				+ "   order by fDistance ASC,fDate DESC";
		return sql;
	}

	/**
	 * 获取分享内容的基本信息
	 * 
	 * @param fUserID 用户ID，用于判断是否有关注等
	 * @param fID     Share的FID
	 * 
	 * @return
	 */
	public static String getShareBaseInfoSql(String fUserID, String fID) {
		String sql = "select fID,fType,fTitle,fText,fDate ,fUserID,DATEDIFF(Minute, fDate, SYSUTCDATETIME()) as 'fDiffDate',\n"
				+ "(select fID from Club where fCreatorID=fUserID) as 'fClubId',\n"
				+ "(select COUNT (*) from Relation where Relation.fUserID='" + fUserID
				+ "' and Relation.fByUserID=Share.fUserID and Relation.fType=0) as 'fIsFollow',\n"
				+ "(select COUNT(fID) from Favorite where fUserID='" + fUserID
				+ "' and fShareID=Share.fID) as 'fIsFavorite',\n"
				+ "(select fNickName from Users where Users.fID=Share.fUserID) as 'fNickName',\n"
				+ "(select top 1 fImageURL from UserImages where UserImages.fUserID=Share.fUserID and fIsAvatar=1) as 'fUserPic',\n"
				+ "(select COUNT(fID)from ShareVisitor where fShareID=Share.fID)as 'fVisitCount',\n"
				+ "(select fLatitude from Address where Address.fID= fPostAddressID) as 'fPostLatitude',\n"
				+ "(select fLongitude from Address where Address.fID= fPostAddressID) as 'fPostLongitude',\n"
				+ "(select fAddress from Address where Address.fID= fPostAddressID) as 'fPostAddress',\n"
				+ "(select fCity from Address where Address.fID= fPostAddressID) as 'fPostCity',\n"
				+ "(SELECT STUFF((SELECT ';' + fImageURL FROM ShareImages where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fImages',\n"
				+ "(SELECT STUFF((SELECT ';' + fVideoURL FROM ShareVideos where fShareId=Share.fID order by fDate  for xml path('')),1,1,''))as 'fVideos'\n"
				+ "from Share  where fID='" + fID + "'";
		return sql;
	}

	/**
	 * 获取share的点赞人数
	 * 
	 * @param fShareId
	 * @return
	 */
	public static String getShareLikeUsersCountSql(String fShareId) {
		String sql = "select COUNT(fId)as 'fCount' from Favorite where fShareID='" + fShareId + "'";
		return sql;
	}

	/**
	 * 获取share的点赞人数列表
	 * 
	 * @param fShareId
	 * @param page
	 * @param size
	 * @return
	 */
	public static String getShareLikeUsersSql(int page, int size, String fShareId) {
		String WHERE_STRING = "fShareID='" + fShareId + "'";
		String SQL_QUERY_FLD = "Favorite.fUserID as 'fUserID',\n"
				+ " (select fNickName from Users where fUserID=Users.fID) as 'fNickName',\n"
				+ " (select fImageURL from UserImages where fUserID=Favorite.fUserID and fIsAvatar=1) as'fUserPic',\n"
				+ " Favorite.fDate as 'fFavoriteDate'\n";
		String SORT_FLD = "Favorite.fDate";
		String sql = BaseSqlUtils.getPageSqlOfMe(SQL_QUERY_FLD, "Favorite", PRIMARY_KEY, SORT_FLD, size, page,
				WHERE_STRING);
		return sql;
	}

	/**
	 * 获取其他博客
	 * 
	 * @param fUserID  登录的用户id
	 * @param fShareID 当前博客id
	 * @return
	 */
	public static String getOtherShareSql(String fUserID, String fShareID) {
		String sql = "select top 10 fID,fType,fTitle,fText,fDate ,fUserID,\n"
				+ " (select top 1 fImageURL FROM ShareImages where fShareId=Share.fID order by fDate)as 'fImages',\n"
				+ " (select top 1 fVideoURL FROM ShareVideos where fShareId=Share.fID order by fDate)as 'fVideos'\n"
				+ "from Share\n" + "where fType=0 and fUserID =("
				+ ("select top 1 fUserID from Share where fID='" + fShareID + "'") + ")\n"
				+ "and (fAuthority=0 or (fAuthority=1 and Share.fUserID='" + fUserID + "')\n"
				+ "or(fAuthority=2 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel>0 and fUserID='"
				+ fUserID + "')>0)\n"
				+ "or (fAuthority=3 and (select COUNT(fId) from ShareAuthorithy where fShareID=Share.fID and fLevel=0 and fUserID='"
				+ fUserID + "')=0))\n" + "and fID <> '" + fShareID + "'\n" + "order by fDate DESC";
		return sql;
	}

}
