<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="boot.htm"%>
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
       
 <form action="searchAuthor" >
  <button class="btn btn-outline-danger my-2 my-sm-0" type="submit" id="searchAuthor1" style="float:right;">Search</button>       
<div style="float: right;">
	<input class="form-control mr-sm-2" type="text"
		placeholder="Search Author" aria-label="Search" id="searchString" name="searchString">
	<input type="hidden" value="1" name="pageNo" id="pageNo"></input>	
</div>	
 </form>
 
<br/>

<div id="tabletest"></div>
<div class="container" id="authorInfo" style="text-align: center; ">
	<h4 style="padding-left: 20%">
		 Total Authors in LMS:
		${totalCount}
   </h4>
   <br /> 
   <loop:if test="${empty searchString}">
   <nav aria-label="Page navigation example">
		<ul class="pagination" style="padding-left: 38%">
			<li class="page-item"
			<loop:if test="${pageNo eq 1}">
                style="display:none"
            </loop:if>
			><a class="page-link" href="viewauthors?pageNo=${pageNo-1}"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
        
            <loop:forEach begin="1" end="${numOfPages}" var="i">
			<li class="page-item"><a class="page-link"
				href="viewauthors?pageNo=${i}">${i}</a></li>
			</loop:forEach>
			<li class="page-item"  
			  <loop:if test="${pageNo == numOfPages}">
                style="display:none"
            </loop:if>
			
			><a class="page-link" href="viewauthors?pageNo=${pageNo+1}"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
   
   </loop:if>
   
   <loop:if test="${not empty searchString}">
   
	<nav aria-label="Page navigation example">
		<ul class="pagination" style="padding-left: 38%">
			<li class="page-item"
			<loop:if test="${pageNo eq 1}">
                style="display:none"
            </loop:if>
			><a class="page-link" href="searchAuthor?searchString=${searchString }&pageNo=${pageNo-1}"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
        
            <loop:forEach begin="1" end="${numOfPages}" var="i">
			<li class="page-item"><a class="page-link"
				href="searchAuthor?searchString=${searchString }&pageNo=${i}">${i}</a></li>
			</loop:forEach>
			<li class="page-item"  
			  <loop:if test="${pageNo == numOfPages}">
                style="display:none"
            </loop:if>
			
			><a class="page-link" href="searchAuthor?searchString=${searchString }&pageNo=${pageNo+1}"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
  </loop:if>
	 <table class="table table-striped" id="authorTable">
		<tr>
			<th>#</th>
			<th>Author Name</th>
			<th>Books Written</th>
			<th>Edit Author</th>
			<th>Delete Author</th>
		</tr>
	<loop:forEach var="a" items="${authors}" varStatus="authorsLoop">
		<tr>
		<td> ${authorsLoop.index+1} </td>
		<td>${a.authorName} </td>
		<td>
				<ul style="padding-right: 50%; list-style-type: none;">
					<loop:forEach var="b" items="${a.books}">
					<li>${b.title}</li>
					</loop:forEach>
				</ul>
		</td>
		<td>
				<button type="button" class="btn btn-primary btn-sm"
					data-toggle="modal"
					data-remote="editauthor?authorId=${a.authorId}"
					data-target="#myModel">Edit</button>
		</td>			
			<td>
				<button type="button"
					onclick="javascript:location.href='deleteAuthor?authorId=${a.authorId}'"
					class="btn btn-danger btn-sm">Delete</button>
			</td>
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