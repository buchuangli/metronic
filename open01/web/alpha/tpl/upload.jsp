<jsp:useBean id="upload" scope="session"
	class="com.open01.logs.web.UploadBean" />
<%
	upload.processRequest(request, response, out);
%>