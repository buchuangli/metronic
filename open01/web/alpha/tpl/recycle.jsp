<jsp:useBean id="recycle" scope="session"
	class="com.open01.logs.web.RecycleBean" />
<%
	recycle.processRequest(request, response, out);
%>
