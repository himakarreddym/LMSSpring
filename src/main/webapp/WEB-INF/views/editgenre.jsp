<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>


<div class="container"style="text-align: center;margin-top: 45px;">
    
	<form:form method="post" action="editgenre" modelAttribute="Genre" >
		${statusMessage}
		<br/>Enter Genre Name to Edit: <form:input type="text" path="genreName" /><br />
		<form:input type="hidden" path="genreId" /><br/>
		
		<div class="form-group" style="float: left;width: 46%;padding-left: 2%">
		<label for="sel1">Select Books from list Below: </label>
		<select class="form-group" id="sel1" multiple="multiple" size="10" name="bookIds" style="width:80%;">
			<loop:forEach items="${books}" var="b">
								<option value="${b.bookId}"> ${b.title} </option>
							</loop:forEach>
		</select>
		 </div>
		
		<div style="float: left;width: 54%;margin-top: 30px;margin-bottom: 50px;">
        <table class="table table-striped">
    		<tr>
    			<th> Books by this Genre </th>
    			<th> Delete </th>
    		</tr>		
    		<loop:forEach items="${genreBooks}" var="gb">
    		<tr>
			<td>${gb.title}</td>
				<td>
				<button type="button"
				onclick="javascript:location.href='deleteGenreBook?genreId=${Genre.genreId}&bookId=${gb.bookId}'"
				class="btn btn-danger btn-sm">Delete</button>
				</td>
			</tr>
    		</loop:forEach>
         </table>
       </div>  
       <div >
       </div>
       <button type="submit" class="btn btn-primary btn-sm">Update Genre</button><br/><br/> 
	</form:form>
    </div>
    
    
