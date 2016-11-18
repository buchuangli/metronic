<jsp:useBean id="projects" scope="session"
	class="com.open01.logs.web.ProjectsBean" />
<%
	projects.processRequest(request, response, out);
%>
