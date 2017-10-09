<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Author"%>

${statusMessage}
 <div class="jumbotron jumbotron-billboard">
  <div class="img"></div>
    <div class="container">
        <div>
<div class="container"style="text-align:center;margin-top:45px;">
	<h3>List of Borrowers in LMS&nbsp;&nbsp;&nbsp;&nbsp; Total Borrowers in LMS: ${totalCount} Borrowers</h3>
	<nav aria-label="Page navigation example">
		<ul class="pagination" style="padding-left: 38%">
			<li class="page-item"
			<loop:if test="${pageNo eq 1}">
                style="display:none"
            </loop:if>
			><a class="page-link" href="viewborrowers?pageNo=${pageNo-1}"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
            <loop:forEach begin="1" end="${numOfPages}" var="i">
			<li class="page-item"><a class="page-link"
				href="viewborrowers?pageNo=${i}">${i}</a></li>
			</loop:forEach>
			<li class="page-item"  
			  <loop:if test="${pageNo == numOfPages}">
                style="display:none"
            </loop:if>
			
			><a class="page-link" href="viewborrowers?pageNo=${pageNo+1}"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
	
	<table class="table table-striped" >
		<tr>
			<th>#</th>
			<th>Borrower Name</th>
			<th>Borrower Address</th>
			<th>Borrower Phone</th>
			<th>Edit Borrower</th>
			<th>Delete Borrower</th>
		</tr>
		   <loop:forEach var="br" items="${borrowers}" varStatus="borrowersloop">
		<tr>
		<td> ${borrowersloop.index+1} </td>
		<td>${br.name} </td>
        <td>${br.address} </td>
        <td>${br.phone} </td>
        <td><button type="button"
					onclick="javascript:location.href='editborrower?cardNo=${br.cardNo}'"
					class="btn btn-primary btn-sm">Edit</button></td>
			<td><button type="button"
					onclick="javascript:location.href='deleteBorrower?cardNo=${br.cardNo}'"
					class="btn btn-danger btn-sm">Delete</button></td>
		</tr>
	</loop:forEach>
	</table>
</div>
</div>
        </div>
    </div>