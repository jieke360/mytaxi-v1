/**
 * 
 */
package com.great.mytaxi.fe.main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.great.mytaxi.fe.main.impl.BusinessDispathImpl;

/**
 * 
 * 业务请求servlet
 * 
 * @author xu.jianpu
 * 
 *         2012-8-8 上午11:38:19
 */
public class RequestServlet extends HttpServlet {

	private static final Log log = LogFactory.getLog(RequestServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7706689977812360476L;

	/**
	 * Constructor of the object.
	 */
	public RequestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Request type get");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Request type post");
		response.setCharacterEncoding("UTF-8");

		request.setCharacterEncoding("UTF-8"); // 设置接收的编码格式

		int contentLength = request.getContentLength();
		if (log.isInfoEnabled()) {
			log.info("接收到的消息长度为：" + contentLength);
		}
		if (1 > contentLength) {
			log.error("接收到的消息长度为不合法,消息长度为：" + contentLength);
			return;
		}

		// 这里是否用其他的输入缓冲流号点？
		byte[] cs = readXmlContent(request);

		BusinessDispath businessDispath = new BusinessDispathImpl();

		byte[] result = businessDispath.execute(cs);

		// resp.addHeader("Set-cookie", "JSESSIONID=" +
		// req.getSession().getId());
		response.setCharacterEncoding("UTF-8");
		/** 统一处理数据发送 */
		OutputStream os = response.getOutputStream();
		os.write(result);
		try {
			os.flush();
			log.info(">-----------返回响应XML----------<");
			log.info(new String(result, "UTF-8").trim());
		} catch (Exception e) {
			log.error("客户端[]连接断开，返回响应结果失败！");
		}
	}

	/**
	 * 
	 * 读取输入流中xml的内容
	 * 
	 * @param request
	 * @throws IOException
	 */
	private byte[] readXmlContent(HttpServletRequest request)
			throws IOException {
		BufferedInputStream bs = new BufferedInputStream(
				request.getInputStream());
		byte[] cs = new byte[request.getContentLength()];
		byte m = 0;
		int j = 0;
		while ((m = (byte) bs.read()) != -1) {
			cs[j] = m;
			j++;
		}
		log.debug("实际接收到的内容的长度为：" + j);

		return cs;

	}
}
