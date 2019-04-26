package org.itzheng.servlet.user;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itzheng.sqlstr.BaseSqlUtils;
import org.itzheng.sqlstr.UserSqlStr;
import org.itzheng.table.Users;
import org.itzheng.utils.JsonUtils;
import org.itzheng.utils.ServletUtils;
import org.itzheng.utils.StrUtils;
import org.itzheng.utils.db.MyDBOperationHelper;

/**
 * @api {post} /api/user/login Login:用户登录
 * @apiVersion 1.0.1
 * @apiName Login
 * @apiGroup User
 *
 * @apiParam {String} fUserName 用户名.
 * @apiParam {String} fPassword 密码.
 *
 * @apiSuccess {String} fID 用户唯一id.
 * @apiSuccess {Boolean} fStatus 用户状态.
 * @apiSuccess {String} fEmail 用户邮箱.
 * @apiSuccess {String} fUserName 用户名.
 * @apiSuccess {String} fPassword 密码.
 * @apiSuccess {String} fNickName 昵称.
 * @apiSuccess {Integer} fSex 性别 0女 1男.
 * @apiSuccess {Date} fBirthday 生日.
 * @apiSuccess {String} fIntroduce 自我介绍.
 * @apiSuccess {Date} fDate 最后修改时间.
 */
@WebServlet("/api/user/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> fieldsValues = JsonUtils.jsonToMap(ServletUtils.getPostBodyString(request));
		String fUserName = BaseSqlUtils.escapeSql(fieldsValues.get(Users.fUserName));
		String fPassword = StrUtils.toTrimAll(BaseSqlUtils.escapeSql(fieldsValues.get(Users.fPassword)));
		MyDBOperationHelper.queryAndSetResponse(UserSqlStr.getLoginSql(fUserName, fPassword), response);

	}

}
