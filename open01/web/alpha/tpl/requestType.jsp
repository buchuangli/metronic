<jsp:useBean id="re" scope="session"
	class="com.open01.logs.web.RequestTypeBean" />
<%
	re.processRequest(request, response, out);
%>
