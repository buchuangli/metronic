<jsp:useBean id="bro" scope="session"
	class="com.open01.logs.web.BrowserBean" />
<%
	bro.processRequest(request, response, out);
%>
