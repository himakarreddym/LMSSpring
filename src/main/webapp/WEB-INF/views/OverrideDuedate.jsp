<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
${statusMessage}
<div class="container" style="text-align: center;margin-top:45px;">	
		<div>
		<label for="sel1">Select Book to Override the due date from list Below: </label>
		<nav aria-label="Page navigation example">
		<ul class="pagination" style="padding-left: 38%">
			<li class="page-item"
			<loop:if test="${pageNo eq 1}">
                style="display:none"
            </loop:if>
			><a class="page-link" href="OverrideDuedate?pageNo=${pageNo-1}"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
            <loop:forEach begin="1" end="${numOfPages}" var="i">
			<li class="page-item"><a class="page-link"
				href="OverrideDuedate?pageNo=${i}">${i}</a></li>
			</loop:forEach>
			<li class="page-item"  
			  <loop:if test="${pageNo == numOfPages}">
                style="display:none"
            </loop:if>
			
			><a class="page-link" href="OverrideDuedate?pageNo=${pageNo+1}"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
	
	
		
        <table class="table table-striped">
    		<tr>
    			<th> Book Title </th>
    			<th> Branch name </th>
    			<th> Borrower name </th>
    			<th> Date Out</th>
    			<th> Due Date </th>
    			<th> Override </th>
    		</tr>
    
    		<tr>	
    		<loop:forEach var="bl" items="${BookLoans}">
    		<tr>
    			<td>
    			${bl.book.title }
    			 </td>
    			 <td>
    			 ${bl.branch.branchName }
    			 </td>
    			 <td>
    			 ${bl.borrower.name }
    			 </td>
    			 <td>
    			 ${bl.dateOut}
    			 </td>
    			 <td>
    			 ${bl.dueDate }
    			 </td>
    			 <td>
    			 <form  method="post" action="override" >
    		<input type="hidden" name="bookId" value="${bl.bookId}">
    		<input type="hidden" name=branchId value="${bl.branchId}">
    		<input type="hidden" name="cardNo" value="${bl.cardNo}">
    		<input type="hidden" name="dateOut" value="${bl.dateOut}">
    		<input type="hidden" name="dueDate" value="${bl.dueDate}">
    			 <button type="submit"
					class="btn btn-danger btn-sm">Override</button>
					</form>
					</td>
			</tr>		
    		</loop:forEach>
    		</tr>
		
         </table> 
			
    </div>
</div>