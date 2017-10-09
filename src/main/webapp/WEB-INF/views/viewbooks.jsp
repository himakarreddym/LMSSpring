<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
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
        
     <div class="container" style="text-align:center;margin-top:45px;">
	<h3>Books List in LMS&nbsp;&nbsp;&nbsp;&nbsp; Total Books in LMS: ${totalCount} Books</h3>
	<nav aria-label="Page navigation example">
		<ul class="pagination" style="padding-left: 38%">
			<li class="page-item"
			<loop:if test="${pageNo eq 1}">
                style="display:none"
            </loop:if>
			><a class="page-link" href="viewbooks?pageNo=${pageNo}-1"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
        
            <loop:forEach begin="1" end="${numOfPages}" var="i">
			<li class="page-item"><a class="page-link"
				href="viewbooks?pageNo=${i}">${i}</a></li>
			</loop:forEach>
			<li class="page-item"  
			  <loop:if test="${pageNo == numOfPages}">
                style="display:none"
            </loop:if>
			
			><a class="page-link" href="viewbooks?pageNo=${pageNo}+1"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
	
	<table class="table table-striped">
		<tr>
			<th>#</th>
			<th>Book Title</th>
			<th>Publisher Name</th>
			<th>Book Genres </th>
			<th>Author Names</th>
			<th>Edit Book</th>
			<th>Delete Book</th>
		</tr>
		
		<loop:forEach var="b" items="${books}" varStatus="bookloop">
		<tr>
		<td> ${bookloop.index+1} </td>
		<td>${b.title} </td>
				<td>
				 <loop:if test="${not empty b.publisher}">
				 		${b.publisher.publisherName}
				 </loop:if>		 
				</td>
			  <td>
			  
			  <ul style="padding-right: 50%; list-style-type: none;">
					<loop:forEach var="g" items="${b.genres}">
					<li>${g.genreName}</li>
					</loop:forEach>
				</ul>
			</td>
			
			<td>
			
			<ul style="padding-right: 50%; list-style-type: none;">
					<loop:forEach var="a" items="${b.authors}">
					<li>${a.authorName}</li>
					</loop:forEach>
				</ul>
			</td>
			<td>
					<button type="button"
					onclick="javascript:location.href='editbooks?bookId=${b.bookId}'"
					class="btn btn-primary btn-sm">Edit</button>
			</td>
			<td><button type="button"
					onclick="javascript:location.href='deleteBook?bookId=${b.bookId}'"
					class="btn btn-danger btn-sm">Delete</button></td>
		</tr>
	</loop:forEach>
	
	
	</table>
</div>
		</div>
        </div>
    </div>
<!-- Model -->
<div class="modal fade" id="myModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Edit Author</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
			</div>
			<div class="modal-body">
				<p></p>
			</div>
		</div>
	</div>
</div>