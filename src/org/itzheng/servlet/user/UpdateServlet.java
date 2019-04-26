package org.itzheng.servlet.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itzheng.table.Users;
import org.itzheng.utils.JsonUtils;
import org.itzheng.utils.ServletUtils;
import org.itzheng.utils.db.MyDBOperationHelper;
import org.itzheng.utils.db.OnResult;

/**
 * 用户信息更新
 */
@WebServlet("/api/user/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> fieldsValues = JsonUtils.jsonToMap(ServletUtils.getPostBodyString(request));
		MyDBOperationHelper.addFDate(fieldsValues);
		MyDBOperationHelper.updateAndSetResponse(Users.TABLE_NAME, Users.PRIMARY_KEY, fieldsValues, response);
	}

}
