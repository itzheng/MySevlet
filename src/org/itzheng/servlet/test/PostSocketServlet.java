package org.itzheng.servlet.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itzheng.socket.SocketPool;
import org.itzheng.utils.ServletUtils;
import org.itzheng.websocket.WsPool;
@WebServlet("/api/test/postSocket")
public class PostSocketServlet extends HttpServlet {
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostSocketServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String cmd = request.getParameter("cmd");
		SocketPool.sendMessageToAll(cmd);
		response.getWriter().append("cmd: ").append(cmd);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = ServletUtils.getPostBodyString(request);
		SocketPool.sendMessageToAll(message);
	}
}
