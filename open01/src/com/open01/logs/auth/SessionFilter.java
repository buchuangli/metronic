package com.open01.logs.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

	private static final String LOGON_URI = "LOGON_URI";
	private static final String HOME_URI = "HOME_URI";
	private String logon_page;
	private String home_page;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		  // 从部署描述符中获取登录页面和首页的URI
		  logon_page = filterConfig.getInitParameter(LOGON_URI);
		  home_page = filterConfig.getInitParameter(HOME_URI);
		  // System.out.println(logon_page);
		  if (null == logon_page || null == home_page) {
		   throw new ServletException("没有找到登录页面或主页");
		  }
	}
		
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		httpResp.setContentType("text/html");
		httpResp.setCharacterEncoding("utf-8");
		HttpSession session = httpReq.getSession();
		// 得到web应用程序的上下文路径
		String ctxPath = httpReq.getContextPath();
			String username = (String) session.getAttribute("user_name");
			PrintWriter out = httpResp.getWriter();
			// 判断如果没有取到用户信息,就跳转到登陆页面 
			if ((username != null)  &&  !"".equals(username)) {
					chain.doFilter(request, response);
					return;
				} else {
					 out.println("<script language=\"JavaScript\">"
						      + "parent.location.href='" + ctxPath + logon_page + "'"
						      + "</script>");
					//httpReq.getRequestDispatcher(logon_page).forward(httpReq, httpResp);
					return;
				}
			} 
	public void destroy() {
	}
}