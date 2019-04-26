package org.itzheng.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.IIOException;

public class MySocketServer {
	public static final int PORT = 8886;

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					socket2();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	private static void socket2() throws IOException {
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("Server正在运行");
		while (true) {
			// 等待客户端连接
			Socket socket = server.accept();
			System.out.println("socket:" + socket.getRemoteSocketAddress());
			new Thread(new SocketRunnable(socket)).start();
		}
	}

	private static class SocketRunnable implements Runnable {

		Socket socket = null;

		public SocketRunnable(Socket sk) {
			this.socket = sk;
		}

		PrintWriter wtr;
		BufferedReader rdr;

		public void run() {
			if(socket!=null) {
				SocketPool.addUser("", socket);
			}
			System.out.println("SocketRunnable run");
			while (socket != null && !socket.isClosed()) // 一直等客户端的消息
			{
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			BufferedReader in;
			BufferedWriter writer;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				while (socket != null && !socket.isClosed()) // 一直等客户端的消息
				{
					System.out.println("socket.isClosed:" + socket.isClosed());
					System.out.println("socket.isConnected:" + socket.isConnected());
					String receive = in.readLine();
					if (receive == null || receive.isEmpty()) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					System.out.println("客户端发来:" + receive);
					writer.write(receive + '\n'); // '\n'一定要加
					writer.flush();
				}
				System.out.println("socket disConnected");
				SocketPool.removeUser(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
