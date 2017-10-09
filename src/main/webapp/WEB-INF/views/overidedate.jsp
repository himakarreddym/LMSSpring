<%@page import="java.sql.Timestamp"%>
<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
int bookid = Integer.parseInt(request.getParameter("bookId"));
int branchId = Integer.parseInt(request.getParameter("branchId"));
int cardNo =Integer.parseInt(request.getParameter("CardNo"));
Timestamp dateOut = Timestamp.valueOf(request.getParameter("dateOut"));

%>
<div class="container" style="text-align: center;margin-top: 10%">
	<h2>Override new due date</h2>
	<form method="post" action="editdueDate">
		${statusMessage}
		<br/>Select New due date: <input type="date" name="newdueDate"><br />
		<input type="hidden" name="bookId" value="<%=bookid%>">
		<input type="hidden" name="branchId" value="<%=branchId%>">
		<input type="hidden" name="CardNo" value="<%=cardNo%>">
		<input type="hidden" name="dateOut" value="<%=dateOut%>"><br/>
		
		<br/>
		<button type="submit" class="btn btn-primary btn-md">Override</button>
	</form>
</div>