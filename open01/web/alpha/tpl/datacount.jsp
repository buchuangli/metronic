<jsp:useBean id="datacount" scope="session"
	class="com.open01.logs.web.DataCountBean" />
<%
	datacount.processRequest(request, response, out);
%>
