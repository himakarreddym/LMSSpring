<%@page import="java.sql.Timestamp"%>
<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="container" style="text-align: center;margin-top: 10%">
	<h2>Override new due date</h2>
	<form:form method="post" action="editdueDate" modelAttribute="BookLoans">
		${statusMessage}
		<br/>Select New due date: <input type="date" name="newdueDate"><br />
		<form:input type="hidden" path="bookId" />
		<form:input type="hidden" path="branchId" />
		<form:input type="hidden" path="CardNo" />
		<input type="hidden" name="dateout" value="${BookLoans.dateOut}"><br/> 
		
		<br/>
		<button type="submit" class="btn btn-primary btn-md">Override</button>
	</form:form>
</div>