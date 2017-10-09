<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="boot.htm" %>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="container"style="text-align:center;margin-top:10%;padding-right: 40px;">
		${statusMessage}
		<form:form method="post" action="checkcard" modelAttribute="Borrower">
		<br/>Enter your card number 
		<form:input type="number" path="cardNo" /><br /><br /><br />
		 <br/>
		 <button type="submit"class="btn btn-primary btn-md">Submit</button>
	</form:form>
</div>