<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container" style="margin-top: 45px;">
    
    <h3 style="padding-left: 35%;"> Enter the new details of the borrower</h3> <br>
	<form:form method="post" action="editborrower1" modelAttribute="Borrower" style="text-align:center;padding-left: 20%; ">
		${statusMessage}
		
		<table class="table table-striped" style="width: 80%">
		<tr>
			<td>
				Enter Borrower Name to Edit 
				</td>
			<td>
			<form:input type="text" path = "name" value="${Borrower.name}"/>
			</td>
		</tr>
		<tr>	
			<td>
			Enter Borrower Address to Edit:
			</td>
			<td>
			<form:input type="text" path = "address" value="${Borrower.address}"/>
			</td>
		</tr>
		<tr>
			<td>
			Enter Borrower Phone to Edit:
			</td>
			<td>
			 <form:input type="text" path = "phone" value="${Borrower.phone}"/>
			</td>
		</tr>
		<tr>
			<td>
			<form:input type="hidden" path = "cardNo" value="${Borrower.cardNo}"/>
			</td>
		 </tr>
		
       </table> 
       <button type="submit" class="btn btn-primary btn-md" style="margin-right: 20% ">Update Borrower</button>
            
	</form:form>
    </div>
    
    