<jsp:useBean id="analyses" scope="session"
	class="com.open01.logs.web.AnalysisBean" />
<%
	analyses.processRequest(request, response, out);
%>
