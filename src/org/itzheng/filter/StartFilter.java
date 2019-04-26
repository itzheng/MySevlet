package org.itzheng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.itzheng.socket.MySocketServer;
import org.itzheng.websocket.WsServer;
import org.java_websocket.WebSocketImpl;

/**
 * 开始启动过滤器
 * 
 * @author WL001
 *
 */
public class StartFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {

	}

	public void init(FilterConfig arg0) throws ServletException {
		this.startWebsocketInstantMsg();
		startSocketServer();
	}

	/**
	 * 启动socket服务
	 */
	private void startSocketServer() {
		new MySocketServer().start();

	}

	/**
	 * 启动web socket服务
	 */
	public void startWebsocketInstantMsg() {
		WebSocketImpl.DEBUG = false;
		WsServer s;
		s = new WsServer(WsServer.PORT);
		s.start();
	}
}