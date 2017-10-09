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
<div class="container" style="text-align:center; margin-top:2	5px;">
	<h3>Add New Author</h3>	<br>
			${statusMessage}
		
	<form:form method = "POST" action = "addauthor1" modelAttribute="Author">
	 		<form:label path = "authorName"><br />Enter Author Name:</form:label>
            <form:input path = "authorName" type="text"/>
            <br /> 	<br>
		<label for="sel1">Select Books from list Below: </label> <br><br>
		<div class="form-group" style="width:90%;display:inline-block;padding-left: 10%">
			 
		  <select name="bookIds"
				class="form-group" multiple="multiple" size="10" style="width:75%;">
			<loop:forEach items="${Author.books}" var="b" >                                
		        <option value="${b.bookId}"> ${b.title} </option>
		    </loop:forEach> 
			</select>
			
		</div>
		<br />
		<button type="submit" class="btn btn-primary btn-md">Save
			Author</button>
	  </form:form>
</div>
   </div>
    </div>
</div>