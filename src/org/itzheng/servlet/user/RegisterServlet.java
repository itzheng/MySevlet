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
import org.itzheng.utils.StrUtils;
import org.itzheng.utils.db.MyDBOperationHelper;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/api/user/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> fieldsValues = JsonUtils.jsonToMap(ServletUtils.getPostBodyString(request));
		fieldsValues.put(Users.PRIMARY_KEY, StrUtils.getGUID());
		MyDBOperationHelper.addFDate(fieldsValues);
		MyDBOperationHelper.insertAndSetResponse(Users.TABLE_NAME, Users.PRIMARY_KEY, fieldsValues, response);

	}

}
