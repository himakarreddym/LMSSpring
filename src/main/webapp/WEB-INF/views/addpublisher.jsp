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
	<h3 style="padding-left: 40%;">Add New Publisher</h3> <br>
		${statusMessage}
	<form:form method="post" action="addPublisher1" modelAttribute="Publisher" style="text-align:center;padding-left: 15%;">

		<table class="table table-striped" style="width: 80%">
		<tr>
			<td>
				Enter Publisher Name
				</td>
			<td>
			<form:input type="text" path = "publisherName" />
			</td>
		</tr>
		<tr>	
			<td>
			Enter Publisher Address
			</td>
			<td>
			<form:input type="text" path = "publisherAddress" />
			</td>
		</tr>
		<tr>
			<td>
			Enter Publisher Phone
			</td>
			<td>
			<form:input type="text" path = "publisherPhone" />
			</td>
		</tr>
		
		
       </table> 
		<button type="submit" class="btn btn-primary btn-md"style="margin-right: 15%">Save Publisher</button>
	</form:form>
</div>
 </div>
    </div>
</div>