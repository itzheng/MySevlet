package org.itzheng.servlet.share;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itzheng.sqlstr.BaseSqlUtils;
import org.itzheng.sqlstr.ShareSqlStr;
import org.itzheng.sqlstr.UserSqlStr;
import org.itzheng.table.BaseParams;
import org.itzheng.table.Users;
import org.itzheng.utils.JsonUtils;
import org.itzheng.utils.ServletUtils;
import org.itzheng.utils.StrUtils;
import org.itzheng.utils.db.MyDBOperationHelper;

/**
 * @api {post} /api/share/getShareList GetShareList:获取首页3个列表的数据
 * @apiVersion 1.0.1
 * @apiName GetShareList
 * @apiGroup Share
 * 
 * @apiHeader {String} fUserId 当前登录用的ID.
 * 
 * @apiParam {Integer} fType 类型：0为关注，1为推荐，2为附近的人,默认为0.
 * @apiParam {Integer} fPage 当前页，从1开始.
 * @apiParam {Integer} fSize 每页的大小，比如10.
 * @apiParam {Double} fLatitude 纬度，118.19152923,只有附近的人才需要.
 * @apiParam {Double} fLongitude 精度，24.49333929,只有附近的人才需要.
 *
 * @apiSuccess {String} fID 唯一id.
 * @apiSuccess {Integer} fType 类型，0为普通微博，1为活动.
 * @apiSuccess {String} fUserID 发布者的id.
 * @apiSuccess {Integer} fDiffDate 时间差，单位秒.
 * @apiSuccess {String} fClubId 发布者的俱乐部ID，入股.
 * @apiSuccess {String} fTitle 标题，比如活动标题.
 * @apiSuccess {String} fText 文本内容.
 * @apiSuccess {Boolean} fIsFollow 是否关注.
 * @apiSuccess {Boolean} fIsFavorite 是否收藏.
 * @apiSuccess {String} fNickName 用户昵称.
 * @apiSuccess {String} fUserPic
 *             用户头像：/iSup/Users/201901/2A6E426E8CF842118F003724D53B67EC.jpg.
 * @apiSuccess {Integer} fVisitCount 访问数.
 * @apiSuccess {Integer} fCommentsCount 评论数.
 * @apiSuccess {Double} fPostLatitude 发送的纬度.
 * @apiSuccess {Double} fPostLongitude 发送的经度.
 * @apiSuccess {Integer} fDistance 距离.
 * @apiSuccess {Integer} fPostCity 发送城市.
 * @apiSuccess {Integer} fPostAddress 发送地址.
 * @apiSuccess {String} fImages 图像集合.
 * @apiSuccess {String} fVideos
 *             视频集合:/iSup/Share/201902/D9154F981DE44AB5A297300E33DB26D2.mp4.
 * @apiSuccess {Date} fDate 最后修改时间.
 */
@WebServlet("/api/share/getShareList")
public class SharesListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SharesListServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 用户参数
		String fUserId = BaseParams.getFUserID(request);
		Map<String, Object> fieldsValues = JsonUtils.jsonToMap(ServletUtils.getPostBodyString(request));
		int page = BaseParams.getFPage(fieldsValues);
		int size = BaseParams.getFSize(fieldsValues);
		int type = BaseParams.getFType(fieldsValues);
		double latitude = BaseParams.getFLatitude(fieldsValues);
		double longitude = BaseParams.getFLongitude(fieldsValues);
		String sql = "";
		switch (type) {
		case 0:
		default:
			sql = ShareSqlStr.getShareByFollowSql(fUserId, page, size);
			break;
		case 1:
			sql = ShareSqlStr.getShareByForyouSql(fUserId, page, size);
			break;
		case 2:
			sql = ShareSqlStr.getShareByNearBySql(fUserId, page, size, latitude, longitude);
			break;
		}
		MyDBOperationHelper.queryAndSetResponse(sql, response);
	}

}
