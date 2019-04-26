package org.itzheng.servlet.share;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itzheng.sqlstr.CommentsSqlStr;
import org.itzheng.sqlstr.ShareSqlStr;
import org.itzheng.table.BaseParams;
import org.itzheng.utils.JsonUtils;
import org.itzheng.utils.ServletUtils;
import org.itzheng.utils.StrUtils;
import org.itzheng.utils.db.MyDBOperationHelper;
import org.itzheng.utils.db.bean.base.BaseResult.DsERPBean;
import org.itzheng.utils.db.bean.base.MapResult;
import org.itzheng.utils.db.bean.result.ShareDetailsResult;
import org.itzheng.utils.db.bean.result.ShareDetailsResult.Item;

/**
 * @api {post} /api/share/getShareDetails GetShareDetails:获取博客详情
 * @apiVersion 1.0.1
 * @apiName GetShareDetails
 * @apiGroup Share
 * 
 * @apiHeader {String} fUserId 当前登录用的ID.
 * 
 * @apiParam {String} fID Share的FID
 * 
 * @apiParamExample {json} Request-Example:
 * {"fID":"F2E096D7061A4B20A2BEDC7E1EB2181B"}
 * 
 * @apiSuccessExample {json} Success-Response:
 * {
  "dsERP": {
    "total": 1,
    "rows": [
      {
        "baseContent": {
          "fID": "F2E096D7061A4B20A2BEDC7E1EB2181B",
          "fType": "0",
          "fTitle": "",
          "fText": "啦啦啦",
          "fDate": "2019-01-10 10:03.52.517",
          "fUserID": "E95A1919BB1245D1B2B21BA711867A67",
          "fDiffDate": 106342,
          "fClubId": "EA66B2AAA56B49AEA28A2ABB448E5070",
          "fIsFollow": 0,
          "fIsFavorite": 1,
          "fNickName": "Nickolaser",
          "fUserPic": "https://img4q.duitang.com/uploads/item/201507/11/20150711135543_RKczs.jpeg",
          "fVisitCount": 37,
          "fPostLatitude": 24.955192,
          "fPostLongitude": 118.150092,
          "fPostAddress": "22222222222222222222",
          "fPostCity": "Xiamen",
          "fImages": "",
          "fVideos": "https://media.w3.org/2010/05/sintel/trailer.mp4"
        },
        "fFavoriteCount": 1,
        "likeUsers": [
          {
            "fUserID": "039D0C44474F471DAF4753B5B06000F8",
            "fNickName": "nell",
            "fUserPic": "/iSup/Users/201901/2A6E426E8CF842118F003724D53B67EC.jpg",
            "fFavoriteDate": "2019-01-18 16:43.51.040"
          }
        ],
        "comments": [
          {
            "fID": 1,
            "fText": "111111111111",
            "fDate": "2019-01-12 00:00.00.000",
            "fUserID": "E95A1919BB1245D1B2B21BA711867A67",
            "fNickName": "Nickolaser",
            "fUserPic": "https://img4q.duitang.com/uploads/item/201507/11/20150711135543_RKczs.jpeg",
            "fCount": 3,
            "subComment": [
              {
                "fID": 2,
                "fPID": 1,
                "fText": "2222",
                "fDate": "1899-12-30 00:00.00.000",
                "fUserID": "E95A1919BB1245D1B2B21BA711867A67",
                "fNickName": "Nickolaser",
                "fToNickName": ""
              },
              {
                "fID": 11,
                "fPID": 1,
                "fText": "嗯呐",
                "fDate": "2019-01-15 16:54.42.097",
                "fUserID": "039D0C44474F471DAF4753B5B06000F8",
                "fNickName": "nell",
                "fToNickName": "Nickolaser"
              }
            ]
          }
        ],
        "otherShares": [
          {
            "fID": "A5C7798E6D414A8FB834091247B28460",
            "fType": "0",
            "fTitle": "",
            "fText": "测试地址能否发送",
            "fDate": "2019-01-16 10:17.16.823",
            "fUserID": "E95A1919BB1245D1B2B21BA711867A67",
            "fImages": "",
            "fVideos": ""
          }
        ]
      }
    ]
  },
  "__result": 0
}
 */
@WebServlet("/api/share/getShareDetails")
public class ShareDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShareDetailsServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 用户参数
		String fUserId = BaseParams.getFUserID(request);
		Map<String, Object> fieldsValues = JsonUtils.jsonToMap(ServletUtils.getPostBodyString(request));
		String fID = BaseParams.getFID(fieldsValues);
		CommentsSqlStr.getReplyCommentSql(1,fUserId, fID, "评论啥呢");
		String resultJson = processResultJson(fUserId, fID);
		ServletUtils.setResponse(response, resultJson);
	}

	/**
	 * 根据fid去获取对应的服务器数据，处理Sql，最后返回Json语句
	 * 
	 * @param fUserId
	 * @param fID
	 */
	private String processResultJson(String fUserID, String fID) {
		ShareDetailsResult finalResult = ShareDetailsResult.newInstance();
		// 获取基本信息
		MapResult baseInfo = MyDBOperationHelper.queryResult(ShareSqlStr.getShareBaseInfoSql(fUserID, fID));
		if (baseInfo.__result == 0) {
			// 设置基本信息
			finalResult.setBaseInfo(MyDBOperationHelper.processMapResult(baseInfo));
			// 设置点赞人数
			queryLikeUsers(finalResult, fUserID, fID);
			// 设置评论人数
			queryComments(finalResult, fUserID, fID);
			// 设置其他微博
			queryOtherBlogs(finalResult, fUserID, fID);
		} else {
			// 数据错误，返回错误信息
			return JsonUtils.toJson(baseInfo);
		}
		finalResult.finish();
		return JsonUtils.toJson(finalResult);

	}

	/**
	 * 联网获取点赞用户相关信息
	 * 
	 * @param finalResult
	 * @param fUserID
	 * @param fShareID
	 */
	private void queryLikeUsers(ShareDetailsResult finalResult, String fUserID, String fShareID) {
		int count = MyDBOperationHelper.queryCount(ShareSqlStr.getShareLikeUsersCountSql(fShareID));
		finalResult.setLikeUsersCount(count);
		if (count > 0) {
			String sql = ShareSqlStr.getShareLikeUsersSql(page, size, fShareID);
			finalResult.setLikeUsers(MyDBOperationHelper.queryMaps(sql));
		}
	}

	public static final int page = 1, size = 10;

	/**
	 * 联网获取点赞用户相关信息
	 * 
	 * @param finalResult
	 * @param fUserID
	 * @param fShareID
	 */
	private void queryComments(ShareDetailsResult finalResult, String fUserID, String fShareID) {
		String sql = CommentsSqlStr.getCommentsSql(page, size, fShareID);
		// 评论还需要嵌套子评论
		List<Map<String, Object>> comments = MyDBOperationHelper.queryMaps(sql);
		for (int index = 0; index < comments.size(); index++) {
			Map<String, Object> comment = comments.get(index);
			comment.put("subComment", getSubComment(comment.get("fID")));
		}
		finalResult.setComments(comments);
	}

	/**
	 * 获取子评论
	 * 
	 * @param fID
	 * @return
	 */
	private List<Map<String, Object>> getSubComment(Object fID) {
		//获取第一条评论，更多评论需要从其他接口获取
		String sql = CommentsSqlStr.getSubCommentsSql(1, 2,StrUtils.toInt(fID));
		return MyDBOperationHelper.queryMaps(sql);
	}

	/**
	 * 联网获取点赞用户相关信息
	 * 
	 * @param finalResult
	 * @param fUserID
	 * @param fShareID
	 */
	private void queryOtherBlogs(ShareDetailsResult finalResult, String fUserID, String fShareID) {
		String sql = ShareSqlStr.getOtherShareSql(fUserID, fShareID);
		finalResult.setOtherBlogs(MyDBOperationHelper.queryMaps(sql));
	}

}
