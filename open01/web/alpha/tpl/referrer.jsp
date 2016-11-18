<jsp:useBean id="referrer" scope="session"
	class="com.open01.logs.web.ReferrerBean" />
<%
referrer.processRequest(request, response, out);
%>
