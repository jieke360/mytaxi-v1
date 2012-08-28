package com.great.mytaxi.common.web;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 所有action类的父类，都必须继承自此类 ActionSupport实现的TextProvider, LocaleProvider接口用于国际化
 * 
 * @author lizhibo 
 */
public class BaseAct extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(BaseAct.class);

	public static final String MESSAGE = "message";

	public static final String LIST = "list";

	public static final String VIEW = "view";
	
	public static final String VIEW_TAB = "viewTab";

	public static final String ADD = "add";

	/*private Paginable paginable;*/

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected HttpSession session;
	
	protected ServletContext application;

	protected String webroot;

	public void writeJson(Object json) {

		try {

			PrintWriter out = response.getWriter();
			out.write(json.toString());

		} catch (Exception e) {

			logger.warn(e.getMessage());
		}
	}

	/*public UserEntity getUserEntity() {
		return (UserEntity) request.getSession().getAttribute("userInfo");
	}

	public String getUserDept() {
		return this.getUserEntity().getMainDeptId() + "";
	}

	public String getUserDepts() {
		return this.getUserEntity().getDeptIds();
	}

	public String getUserCode() {
		return this.getUserEntity().getCode();
	}

	public Paginable getPaginable() {
		if (paginable == null) {
			paginable = new Pagination();
		}
		return paginable;
	}

	public void setPaginable(Paginable paginable) {
		this.paginable = paginable;
	}*/

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
		this.application = request.getSession().getServletContext();
		this.webroot = request.getSession().getServletContext().getRealPath("/");
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
