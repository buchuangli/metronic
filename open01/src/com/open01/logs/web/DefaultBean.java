/*
 * Open01 Inc.
 *
 * This is the code of Open01 Log Analysis System
 * Copyright (C) Open01 Inc. 2016
 * 
 * All rights reserved.
 * 
 */
package com.open01.logs.web;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.ConditionBean;
import info.chenli.web.jsp.Listener;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.open01.logs.auth.User;

/**
 * Superclass of all the Open01 log analysis web application part beans.
 *
 * Conditional bean, where the condition is always true by default.
 *
 * Provides a templated output framework, to be actually specialized/implemented
 * by the subclasses, in addition to various printing services.
 *
 * @author Chen Li
 * @version 20160808
 */
public class DefaultBean extends ConditionBean implements BooleanCondition {

	private Logger logger = Logger.getLogger(DefaultBean.class.getName());
	private User user;

	// Return a User instance from the HTTP Session.
	User getUser() {
		return this.user;
	}

	//
	static User getUserOfSession(HttpSession thisSession) {
		return (User) thisSession.getAttribute("user");
	}

	// when testing, the informing mail will just be sent to the current user.
	protected static final String INFORMING_MAILING_LIST = Listener.getInitParameter("informing_mailing_list");

	//
	public static void printTopMenu(Writer out, HttpServletRequest request) {

		PrintWriter writer = new PrintWriter(out);
		
		  HttpSession sessions=request.getSession();
		 // User u=(User) sessions.getAttribute("user");
		 String user_name =(String) sessions.getAttribute("user_name");
		
		/*writer.println("		<header>");
		writer.println("			<div class=\"countent\">");
		writer.println("				<div class=\"logo\">");
		writer.println("					<img src=\"img/logo.png\"/>");
		writer.println("				</div><ul class=\"nav\">");
		writer.println("					<a href=\"#/home\"><li><span>首页</span><i></i></li></a><a href=\"#/dosage\"><li>");
		writer.println("					<span>数据管理</span><i></i></li></a><a href=\"#/manage\"><li>");
		writer.println("					<span>项目管理</span><i></i></li></a><a href=\"#/analysis\"><li>");
		writer.println("					<span>分析报表</span><i></i></li></a><a href=\"#/serch\"><li>");
		writer.println("					<span>智能搜索</span><i></i></li></a><a href=\"#/help\"><li>");
		writer.println("					<span>帮助文档</span><i></i></li></a><div>");
		writer.println("					<span>欢迎登录，<span>"+user_name+"</span></span></div><li>");
		writer.println("					<e id=\"moreBtn\"></e></li>");
		writer.println("				</ul>");
		writer.println("			</div>");
		writer.println("			<div class=\"border_bottom\"></div>");
		writer.println("			<div class=\"moreNav\">");
		writer.println("				<div class=\"countent\">");
		writer.println("					<ul class=\"nav\">");
		writer.println("						<a href=\"#/options\" id=\"optionsBtn\"><li>");
		writer.println("						<span><e></e>");
		writer.println("							账户设置</span></li></a><a href=\"#/resetPassword\" id=\"resetPasswordBtn\"><li>");
		writer.println("						<span><e></e>");
		writer.println("							重置密码</span></li></a><a href=\"#/recycle\" id=\"recycleBtn\"><li>");
		writer.println("						<span><e></e>");
		writer.println("							回收站</span></li></a><a  id=\"destroy\"><li>");
		//writer.println("							回收站</span></li></a><a href=\"#/home\"><li>");
		writer.println("						<span><e></e>");
		writer.println("							注销账户</span></li></a><a href=\"#/home\"><li>");
		writer.println("						<span><e></e>");
		writer.println("							返回</span></li></a>");
		writer.println("					</ul>");
		writer.println("				</div>");
		writer.println("			</div>");
		writer.println("		</header>");*/
		 
		
		writer.println("					<span class=\"moreNav_name\">"+user_name+"</span><a href=\"#/options\">账户设置</a><br />");

	}

	protected List<String> errorMessages;
	protected List<String> warningMessages;

	public DefaultBean() {
		super();

		setCondition(this);
	}

	protected DefaultBean(BooleanCondition condition) {
		super(condition);
	}

	public boolean isTrue() {
		return true;
	}

	@Override
	protected final void processRequest() {
		logger.info("DefaultBean : processRequest");

		user = getUserOfSession(session);

		// Initialises the list of error and warning messages.

		if (errorMessages == null) {
			errorMessages = new ArrayList<String>();
		}
		errorMessages.clear();

		if (warningMessages == null) {
			warningMessages = new ArrayList<String>();
		}
		warningMessages.clear();

		// That will cause either 'conditionIsTrue()' or 'conditionIsFalse()'
		// to be invoked, depending on the associated boolean condition.

		super.processRequest();
	}

	@Override
	protected void conditionIsTrue() {
		logger.info("DefaultBean : conditionIsTrue");

		if (doConditionIsTrue()) {
			writer.print("<table class=\"content\" cellspacing=\"0\" cellpadding=\"0\">\n");
			writer.print("<tr>\n");
			writer.print("<td style=\"width:100%;\"><h1>");
			writer.print(getConditionIsTrueLeftPageTitle());
			writer.print("&nbsp;-&nbsp;");
			writer.print(getConditionIsTrueRightPageTitle());
			writer.print("</h1>");

			printPageTop();

			writeConditionIsTrueOutput();

			printWarningMessages();

			printPageBottom();
		}
	}

	protected boolean doConditionIsTrue() {
		return true;
	}

	protected String getConditionIsTrueLeftPageTitle() {
		return getLeftPageTitle();
	}

	protected String getConditionIsTrueRightPageTitle() {
		return getRightPageTitle();
	}

	protected void writeConditionIsTrueOutput() {
	}

	@Override
	protected void conditionIsFalse() {
		logger.info("DefaultBean : conditionIsFalse");

		if (doConditionIsFalse()) {
			writer.print("<table class=\"content\" cellspacing=\"0\" cellpadding=\"0\">\n");
			writer.print("<tr>\n");
			writer.print("<td><h1>");
			writer.print(getConditionIsFalseLeftPageTitle());
			writer.print("&nbsp;-&nbsp;");
			writer.print(getConditionIsFalseRightPageTitle());
			writer.print("</h1>");

			printPageTop();

			writeConditionIsFalseOutput();

			printWarningMessages();

			printPageBottom();
		}
	}

	protected boolean doConditionIsFalse() {
		return true;
	}

	protected String getConditionIsFalseLeftPageTitle() {
		return getLeftPageTitle();
	}

	protected String getConditionIsFalseRightPageTitle() {
		return getRightPageTitle();
	}

	protected void writeConditionIsFalseOutput() {
	}

	@Override
	protected void handleThrowable(Throwable t) {
		super.handleThrowable(t);

		logger.info("DefaultBean : handleThrowable");

		writer.print("<table class=\"content\" cellspacing=\"0\" cellpadding=\"0\">\n");
		writer.print("<tr>\n");
		writer.print("<td><h1>");
		writer.print(getThrowableLeftPageTitle());
		writer.print("&nbsp;-&nbsp;");
		writer.print(getThrowableRightPageTitle());
		writer.print("</h1>");

		printPageTop();

		doHandleThrowable(t);

		printWarningMessages();

		printPageBottom();
	}

	protected String getThrowableLeftPageTitle() {
		return getLeftPageTitle();
	}

	protected String getThrowableRightPageTitle() {
		return getRightPageTitle();
	}

	protected void writePreThrowable(Throwable t) {
		writer.print("<p>Unable to complete the request due to the following errors:</p>");
	}

	protected void doHandleThrowable(Throwable t) {
		logger.info("DefaultBean : doHandleThrowable");

		writePreThrowable(t);

		writer.print("<ol style=\"color:red;\">\n");
		if (errorMessages.isEmpty()) {
			writer.print("<li>");
			String message = t.getMessage();
			Throwable cause = t;
			while (message == null && cause != null) {
				message = cause.getMessage();
				cause = cause.getCause();
			}
			if (message != null && message.length() != 0) {
				writer.print(message.trim());
			}
			writer.print("</li>\n");
		} else {
			for (int i = 0; i < errorMessages.size(); ++i) {
				writer.print("<li>" + errorMessages.get(i) + "</li>\n");
			}
		}
		writer.print("</ol>\n");

		writePostThrowable(t);
	}

	protected void writePostThrowable(Throwable t) {
		writer.print("<p><button onclick=\"javascript:window.history.back();\">Back</button></p>\n");
	}

	protected String getLeftPageTitle() {
		return "Open01";
	}

	protected String getRightPageTitle() {
		return "";
	}

	//
	protected void printPageTop() {
		writer.print("\n</td>\n");
		writer.print("<td>&nbsp;</td>");
		writer.print(
				"<td style=\"valign:middle;\"><a href=\"\" title=\"Open01\"><img border=\"0\" alt=\"\" src=\"icons/open01.png\" /></a></td>");

		writer.print("\n</tr>\n</table>\n");
		writer.print("<hr />\n");
	}

	//
	protected void printWarningMessages() {

		if (!warningMessages.isEmpty()) {
			writer.print("<table>\n");
			writer.print(" <tr>\n");
			writer.print("  <td>\n");
			writer.print("   <p>Warning:</p>\n");
			writer.print("   <ol style=\"color:red;\">\n");

			for (int i = 0; i < warningMessages.size(); ++i) {
				writer.print("    <li>" + warningMessages.get(i) + "</li>\n");
			}

			writer.print("   </ol>\n");
			writer.print("  </td>\n");
			writer.print(" </tr>\n");
			writer.print("</table>\n");
		}
	}

	//
	protected void printPageBottom() {
		/*
		 * Uncomment when need "sign out" on bottom-right of the page
		 * 
		 * String request_uri = request.getRequestURI();
		 * 
		 * int index = request_uri.lastIndexOf("/s/");
		 * 
		 * if (index != -1) { writer.print(
		 * "<table class=\"content\" cellspacing=\"0\" cellpadding=\"0\"><tr><td class=\"rule\" height=\"3\"></td></tr><tr><td align=\"right\" style=\"padding-top:5px;padding-bottom:5px;\"><a href=\""
		 * + request_uri.substring(0, index) + "/" + SignInBean.JSP_NAME +
		 * "?cmd=SIGN:OUT\">Sign Out</a></td></tr></table>\n"); }
		 */
	}

	//
	//
	// Static print-related services.
	//
	//

	static void printLink(PrintWriter writer, String href, String text) {
		writer.print("<p><a href=\"" + href + "\">" + text + "</a></p>\n");
	}

	static void printLink(PrintWriter writer, String href, String target, String text) {
		writer.print("<p><a href=\"" + href + "\" target=\"" + target + "\">" + text + "</a></p>\n");
	}

	static void printBackLink(PrintWriter writer, String href, String text) {
		writer.print("<p><img src=\"icons/prev.png\" align=\"middle\" alt=\"\"/>&nbsp;<a href=\"" + href + "\">" + text
				+ "</a></p>\n");
	}

	static void printForwardLink(PrintWriter writer, String href, String text) {
		writer.print("<p><img src=\"icons/next.png\" align=\"middle\" alt=\"\"/>&nbsp;<a href=\"" + href + "\">" + text
				+ "</a></p>\n");
	}

	static void printDottedLine(PrintWriter writer) {
		printDottedLine(writer, true);
	}

	static void printDottedLine(PrintWriter writer, boolean spacer) {
		// writer.print("<br />\n<table class=\"content\" cellspacing=\"0\"
		// cellpadding=\"0\"><tr><td class=\"rule\"
		// height=\"3\"></td></tr></table>\n");
		writer.print("<br />\n<hr />\n");
		if (spacer) {
			writer.print("<br />\n");
		}
	}

	static void printDottedLine(PrintWriter writer, boolean spacerBefore, boolean spacerAfter) {
		if (spacerBefore) {
			writer.print("<br />\n");
		}
		// writer.print("<table class=\"content\" cellspacing=\"0\"
		// cellpadding=\"0\"><tr><td class=\"rule\"
		// height=\"3\"></td></tr></table>\n");
		writer.print("<hr />\n");
		if (spacerAfter) {
			writer.print("<br />\n");
		}
	}

	//
	static void printFormSeparator(PrintWriter writer) {
		printFormSeparator(writer, 2);
	}

	//
	static void printFormSeparator(PrintWriter writer, int colSpan) {
		writer.print("<tr><td colspan=\"" + colSpan + "\"></td></tr>\n");
		writer.print("<tr><td colspan=\"" + colSpan + "\"></td></tr>\n");
	}

	static void printExport(PrintWriter writer, String uri, String modelId) {

		writer.print("<select>\n");
		writer.print("<option onclick=\"javascript:open_popup('" + uri + modelId + ".cellml');\">JSON</option>\n");
		writer.print("<option onclick=\"javascript:open_popup('" + uri + modelId + ".html');\">EXCEL</option>\n");
		writer.print("</select>\n");
	}

	//
	//
	// Protected version of the above print-related services.
	//
	//

	protected void printLink(String href, String text) {

		printLink(writer, href, text);
	}

	protected void printLink(String href, String target, String text) {

		printLink(writer, href, target, text);
	}

	protected void printBackLink(String href, String text) {

		printBackLink(writer, href, text);
	}

	protected void printForwardLink(String href, String text) {

		printForwardLink(writer, href, text);
	}

	protected void printDottedLine() {
		printDottedLine(writer);
	}

	protected void printDottedLine(boolean spacer) {
		printDottedLine(writer, spacer);
	}

	protected void printFormSeparator() {
		printFormSeparator(writer);
	}

	protected void printFormSeparator(int colSpan) {
		printFormSeparator(writer, colSpan);
	}

	protected void printExport(String uri, String modelId) {

		printExport(writer, uri, modelId);
	}

}
