<jsp:useBean id="conversion" scope="session"
	class="com.open01.logs.web.ConversionBean" />
<%
	conversion.processRequest(request, response, out);
%>
