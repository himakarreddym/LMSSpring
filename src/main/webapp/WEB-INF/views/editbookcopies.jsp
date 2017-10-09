<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
    
	<form:form method="post" action="editBookcopies" modelAttribute="BookCopies" >
		${statusMessage}
		<br/>Enter new number of copies 
		 <form:input type="number" path="copies" value="${BookCopies.copies}"/> <br/>
		<form:input type="hidden" path="bookId" value="${BookCopies.bookId}"/><br/>
		<form:input type="hidden" path="branchId" value="${BookCopies.branchId}"/><br/>
        
		<button type="submit" class="btn btn-primary btn-sm">Update Book copies</button><br/><br/>
    
            
	</form:form>
    </div>
    
    