package org.itzheng.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;
/**
 * 用于保存webSocket连接
 * @author WL001
 *
 */
public class SocketPool {
	private static final Map<Socket, String> wsUserMap = new HashMap<Socket, String>();

	/**
	 * 通过websocket连接获取其对应的用户
	 * 
	 * @param conn
	 * @return
	 */
	public static String getUserByWs(Socket conn) {
		return wsUserMap.get(conn);
	}

	/**
	 * 根据userName获取WebSocket,这是一个list,此处取第一个
	 * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
	 * 
	 * @param user
	 */
	public static Socket getWsByUser(String userName) {
		Set<Socket> keySet = wsUserMap.keySet();
		synchronized (keySet) {
			for (Socket conn : keySet) {
				String cuser = wsUserMap.get(conn);
				if (cuser.equals(userName)) {
					return conn;
				}
			}
		}
		return null;
	}

	/**
	 * 向连接池中添加连接
	 * 
	 * @param inbound
	 */
	public static void addUser(String userName, Socket conn) {
		wsUserMap.put(conn, userName); // 添加连接
	}

	/**
	 * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
	 * 
	 * @return
	 */
	public static Collection<String> getOnlineUser() {
		List<String> setUsers = new ArrayList<String>();
		Collection<String> setUser = wsUserMap.values();
		for (String u : setUser) {
			setUsers.add(u);
		}
		return setUsers;
	}

	/**
	 * 移除连接池中的连接
	 * 
	 * @param inbound
	 */
	public static boolean removeUser(Socket conn) {
		if (wsUserMap.containsKey(conn)) {
			wsUserMap.remove(conn); // 移除连接
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 向特定的用户发送数据
	 * 
	 * @param user
	 * @param message
	 */
	public static void sendMessageToUser(WebSocket conn, String message) {
		if (null != conn && null != wsUserMap.get(conn)) {
			conn.send(message);
		}
	}

	/**
	 * 向所有的用户发送消息
	 * 
	 * @param message
	 */
	public static void sendMessageToAll(String message) {
		Set<Socket> keySet = wsUserMap.keySet();
		synchronized (keySet) {
			for (Socket socket : keySet) {
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					writer.write(message+ '\n');
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
