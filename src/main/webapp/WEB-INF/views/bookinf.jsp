<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="container" style="text-align: center;margin-top: 6%">
		${statusMessage}
			<h3>Book Information  </h3> <br>
		<div class="form-group" style="padding-left: 20%;">
	
        <table class="table table-striped" style="width: 80%;">
    		<tr>
    			<th> Book Title </th>
    			<td>
    			${Book.title}
    			 </td>
    			</tr> 
    			<tr>
    			<th> Publishers name </th> 
    			 <td>
    				${Book.publisher.publisherName}	 <br />
    			 </td>
    			</tr> 
    			<tr>
    			<th> Authors Names </th> 
    			<td>
    			<ul style="padding: 0;list-style-type: none;">
    			
    			 <loop:forEach var="a" items="${Book.authors}">
					<li>${a.authorName}</li>
					</loop:forEach>
			</ul> 
				
			</td>
    			 </tr> 
    			<tr>
    			<th> Genres Names </th> 
    			<td>
    			<ul style="padding: 0;list-style-type: none;">
    			 <loop:forEach var="g" items="${Book.genres}">
					<li>${g.genreName}</li>
					</loop:forEach>
					</ul> 
			</td>
    		</tr>		
    </table> 
         
    </div>
    <button type="button"
					onclick="javascript:location.href='librarian?bookId=${bookId}&branchId=${branchId}'"
					class="btn btn-primary btn-md"> Return to previous </button>
    
</div>