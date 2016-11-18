<jsp:useBean id="staytime" scope="session"
	class="com.open01.logs.web.StayTimeBean" />
<%
	staytime.processRequest(request, response, out);
%>
