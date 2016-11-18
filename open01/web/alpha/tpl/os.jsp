<jsp:useBean id="os" scope="session"
	class="com.open01.logs.web.OperateSystemBean" />
<%
	os.processRequest(request, response, out);
%>
