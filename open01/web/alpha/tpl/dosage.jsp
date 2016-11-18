<jsp:useBean id="dosage" scope="session"
	class="com.open01.logs.web.DosageBean" />
<%
	dosage.processRequest(request, response, out);
%>