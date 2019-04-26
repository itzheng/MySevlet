package org.itzheng.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {
	/**
	 * 设置字符统一为utf-8
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void setCharacter(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type", "text/html;charset=UTF-8");
	}

	/**
	 * 设置响应结果字符
	 * 
	 * @param resp
	 * @param string
	 * @throws IOException
	 */
	public static void setResponse(HttpServletResponse response, String string) {
		// 设置字符集
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.append(string).flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}

	}

	/**
	 * 获取post的body字符串
	 * 
	 * @param request
	 * @return
	 */
	public static String getPostBodyString(HttpServletRequest request) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LogHelper.i("from host:" + request.getRemoteHost() + "," + "path:" + request.getRequestURI().toString());
		LogHelper.i("post:" + buffer);
		return buffer.toString();
	}

	/**
	 * 返回下载文件
	 * 
	 * @param realPath
	 * @param response
	 */
	public static void returnDownloadLocalFile(String realPath, HttpServletResponse response) {
		String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentTypeByFileName(response, fileName);
//		response.setHeader("content-disposition", "attachment;filename=" + fileName);
//		response.setHeader("content-type", "image/jpeg");
		try {
			returnFile(new FileInputStream(realPath), response);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 向客户端返回传入的输入流
	 * 
	 * @param fis
	 * @param response
	 */
	public static void returnFile(FileInputStream fis, HttpServletResponse response) {
		try {
//			FileInputStream fis = new FileInputStream(realPath);
			ServletOutputStream sos = response.getOutputStream();
			int len = 1;
			byte[] b = new byte[1024];
			while ((len = fis.read(b)) != -1) {
				sos.write(b, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generat1ed catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件名，设置内容类型
	 * 
	 * @param response
	 * @param filename
	 */
	public static void setContentTypeByFileName(HttpServletResponse response, String filename) {
//		response.setHeader("content-disposition", "attachment;filename=" + filename);
		response.setHeader("content-disposition", "filename=" + filename);
		String contentType = null;
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);
		switch (suffix.toLowerCase()) {
		// https://www.jb51.net/article/32773.htm
		case "mp4":
			contentType = "video/mp4";
			break;
		case "jpg":
			contentType = "image/jpeg";
			break;
		case "png":
			contentType = "image/png ";
			break;
		case "gif":
			contentType = "image/gif";
			break;
		default:
			response.setContentType("text/html;charset=utf-8");
			break;
		}
		System.out.println("contentType:" + contentType);
		response.setContentType(contentType);
	}
}
