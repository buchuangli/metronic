<jsp:useBean id="options" scope="session"
	class="com.open01.logs.web.OptionsBean" />
<%
options.processRequest(request, response, out);
%>
