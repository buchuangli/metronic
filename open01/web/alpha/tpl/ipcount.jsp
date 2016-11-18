<jsp:useBean id="ips" scope="session"
	class="com.open01.logs.web.IpCountBean" />
<%
	ips.processRequest(request, response, out);
%>
