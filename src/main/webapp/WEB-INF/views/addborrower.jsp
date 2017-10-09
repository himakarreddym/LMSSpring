<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="jumbotron jumbotron-billboard">
  <div class="img"></div>
    <div class="container">
        <div>
<div class="container"style="margin-top: 45px;">
<h3 style="padding-left: 40%;">Add New Borrower</h3> <br>
	<form:form method="post" action="addBorrower1" modelAttribute="Borrower"style="text-align:center;padding-left: 15%;">	
		${statusMessage}
		<table class="table table-striped" style="width: 80%">
		<tr>
			<td>
				Enter Borrower Name
				</td>
			<td>
			<form:input type="text" path = "name" />
			</td>
		</tr>
		<tr>	
			<td>
			Enter Borrower Address
			</td>
			<td>
			<form:input type="text" path = "address" />
			</td>
		</tr>
		<tr>
			<td>
			Enter Borrower Phone number
			</td>
			<td>
			 <form:input type="text" path = "phone" />
			</td>
		</tr>
		
		
       </table> 
		<button type="submit" class="btn btn-primary btn-md"style="margin-right: 15%">Save Borrower</button>
	</form:form>
</div>
 </div>
    </div>
</div>