<jsp:useBean id="serch" scope="session"
	class="com.open01.logs.web.SerchBean" />
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%
	serch.processRequest(request, response, out);
%>
