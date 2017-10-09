<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <div class="jumbotron jumbotron-billboard">
  <div class="img"></div>
    <div class="container">
        <div>
        
<div class="container" style="margin-top: 45px;">
     <h3 style="padding-left: 35%;"> Enter the new details of the Publisher</h3> <br>
	<form:form method="post" action="editpublisher1" modelAttribute="Publisher" style="text-align:center;padding-left: 20%; ">
		${statusMessage}
		<table class="table table-striped" style="width: 80%">
		<tr>
			<td>
				Enter Publisher Name to Edit
				</td>
			<td>
			<form:input type="text" path = "publisherName" value="${Publisher.publisherName}"/>
			</td>
		</tr>
		<tr>	
			<td>
			Enter Publisher Address to Edit
			</td>
			<td>
			<form:input type="text" path = "publisherAddress" value="${Publisher.publisherAddress}"/>
			</td>
		</tr>
		<tr>
			<td>
			Enter Publisher Phone to Edit
			</td>
			<td>
			<form:input type="text" path = "publisherPhone" value="${Publisher.publisherPhone}"/>
			</td>
		</tr>
		
        <tr>
			<td>
			<form:input type="hidden" path = "publisherId" value="${Publisher.publisherId}"/>
			</td>
		 </tr>
		
       </table> 
        
		<button type="submit" class="btn btn-primary btn-md"style="margin-right: 20% ">Update Publisher</button><br/><br/>
       
            
	</form:form>
    </div>
       </div>
    </div>
</div>
    