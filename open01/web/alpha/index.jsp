<jsp:useBean id="login" scope="session"
	class="com.open01.logs.web.LoginBean" />
<%
	login.processRequest(request, response, out);
%>
