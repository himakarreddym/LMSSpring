<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<div class="container" style="text-align: center;margin-top: 45px;">
	<h3>Add New Genre</h3> <br>
	<form:form method="post" action="addgenre" modelAttribute="Genre">
	${statusMessage}
		<br/>Enter Genre Name: 
		<form:input path = "genreName" type="text"/>
		 <br/>
		
		<%-- Div class for select tag --%> 
		<label for="sel1">Select Books from list Below: </label>
		<div class="form-group" style="padding-left: 22%;">
		
		<form:select path="books"
				class="form-group" multiple="multiple" size="10" style="width:75%;">
				<loop:forEach items="${books}" var="b" >                                
		        <form:option value="${b.bookId}"> ${b.title} </form:option>
		    </loop:forEach> 
			</form:select>  
		 </div>
		 
		<br/>
		<button type="submit" class="btn btn-primary btn-md">Save Genre</button>
	</form:form>
</div>
   </div>
    </div>
</div>