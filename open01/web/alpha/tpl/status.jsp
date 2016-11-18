<jsp:useBean id="status" scope="session"
	class="com.open01.logs.web.StatusBean" />
<%
	status.processRequest(request, response, out);
%>
