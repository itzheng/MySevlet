package org.itzheng.websocket;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
/**
 * WebSocketServer
 * @author WL001
 *
 */
public class WsServer extends WebSocketServer {
	public static final int PORT=8887;
	public WsServer(int port) {
		super(new InetSocketAddress(port));
	}

	public WsServer(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// ws连接的时候触发的代码，onOpen中我们不做任何操作
		System.out.println("onOpen");
		conn.send("欢迎您！");
		userJoin(conn, "");// 用户加入

	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		// 断开连接时候触发代码
		System.out.println("onClose"+reason);
		userLeave(conn);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("onMessage:"+message);
		if (null != message && message.startsWith("online")) {
			String userName = message.replaceFirst("online", message);// 用户名
			userJoin(conn, userName);// 用户加入
		} else if (null != message && message.startsWith("offline")) {
			userLeave(conn);
		}

	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		// 错误时候触发的代码
		System.out.println("on error");
		ex.printStackTrace();
	}

	/**
	 * 去除掉失效的websocket链接
	 * 
	 * @param conn
	 */
	private void userLeave(WebSocket conn) {
		WsPool.removeUser(conn);
	}

	/**
	 * 将websocket加入用户池
	 * 
	 * @param conn
	 * @param userName
	 */
	private void userJoin(WebSocket conn, String userName) {
		WsPool.addUser(userName, conn);
	}

}
