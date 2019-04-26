package org.itzheng.servlet.media;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itzheng.utils.ArrayUtils;
import org.itzheng.utils.ServletUtils;

/**
 * @api {get} /api/media/getMedia GetMedia:获取图片视频
 * @apiVersion 1.0.1
 * @apiName GetMedia
 * @apiGroup Media
 * @apiParam {String} path 文件路径：如
 *           /iSup/Share/201902/1F821252DA354B96BAD2EC680B1AD942.mp4.
 * @apiExample {curl} Example usage:
 *             ?path=/iSup/Share/201902/1F821252DA354B96BAD2EC680B1AD942.mp4
 */
@WebServlet("/api/media/getMedia")
public class GetMediaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 获取文件的url路径
	 */
	private static final String MEDIA_URL_PATH = "http://192.168.4.20:8081/FtpFiles/";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMediaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("text/html;charset=utf-8");
		String realUrl = MEDIA_URL_PATH + request.getParameter("path");
		log("realUrl:" + realUrl);
		String filename = realUrl.substring(realUrl.lastIndexOf("/") + 1);
		log("filename:" + filename);
		response.setHeader("content-disposition", "filename=" + filename);
//		setContentType(response, realUrl);
//		setFileDownload(response, realUrl);
		URLConnection connection = null;
		java.io.InputStream input;
		OutputStream output;
		try {
			connection = new URL(realUrl).openConnection();
			// 获取响应头内容
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			List<String> contentType = headerFields.get("content-type");
			if (ArrayUtils.isEmpty(contentType) || contentType.get(0).contains("text/html")) {
				//向客户端返回默认图片
				String realPath= getServletContext().getRealPath("/")+ "/WEB-INF/classes/"+"图片.png";
				input=new FileInputStream(realPath);
				ServletUtils.returnDownloadLocalFile(realPath, response);
			}else {
				//服务器图片
				setHeaders(response, headerFields);
				input = connection.getInputStream();
			}
			output = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i = 0;
			while ((i = input.read(buffer)) != -1) {
				output.write(buffer, 0, i);
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将请求到的头部返回到结果去
	 * 
	 * @param response
	 * @param headerFields
	 */
	private void setHeaders(HttpServletResponse response, Map<String, List<String>> headerFields) {
		for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
			String key = entry.getKey();
			if (null != key && !"".equals(key)) {
				List<String> value = entry.getValue();
				if (null != value && value.size() != 0)
					response.setHeader(key, value.get(0));
			}
		}
	}

	private void setContentType(HttpServletResponse response, String realUrl) {
		String filename = realUrl.substring(realUrl.lastIndexOf("/") + 1);
		log("filename:" + filename);
		response.setHeader("content-disposition", "attachment;filename=" + filename);
		String contentType = null;
//		String endName = realUrl.substring(realUrl.lastIndexOf(".") + 1);
		String suffix = realUrl.substring(realUrl.lastIndexOf(".") + 1);
		log("endName:" + suffix);
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
		log("contentType:" + contentType);
		response.setContentType(contentType);
	}

	/**
	 * 
	 * 启动下载
	 * 
	 * @param response
	 * @param realUrl
	 */
	private void setFileDownload(HttpServletResponse response, String realUrl) {
		// TODO Auto-generated method stub
		try {
			// 找到最后的\然后取后面的文件名
			String filename = realUrl.substring(realUrl.lastIndexOf("/") + 1);
			log("filename:" + filename);
			// 设置文件编码
			filename = URLEncoder.encode(filename, "UTF-8");
			// 转成utf-8 ，返回String //创建字节输出流
			OutputStream out = response.getOutputStream(); // 此方法获取输出流对象 //告知客户端下载文件
			// 启动下载 //规定后缀名
			response.setHeader("content-disposition", "attachment;filename=" + filename);
			response.setHeader("content-type", "image/jpeg"); // 执行输出操作
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
