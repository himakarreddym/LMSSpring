<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%AdminService service = new AdminService();
Genre genre = service.readGenreByPK(Integer.parseInt(request.getParameter("genreId")));
List<Book> books = service.readBooks();
List<Book> genreBooks = genre.getBooks();
%>

<div class="container"style="text-align: center;margin-top: 45px;">
    
	<form method="post" action="editGenre">
		${statusMessage}
		<br/>Enter Genre Name to Edit: <input type="text" name="genreName" value="<%=genre.getGenreName()%>"><br />
		<input type="hidden" name="genreId" value="<%=genre.getGenreId()%>"><br/>
		
		<div class="form-group" style="float: left;width: 46%;padding-left: 2%">
		<label for="sel1">Select Books from list Below: </label>
		<select class="form-group" id="sel1" multiple="multiple" size="10" name="bookIds" style="width:80%;">
			<option value="">Select Book to associate</option>
			<%for(Book b: books) {
			if(! genreBooks.contains(b)) { %>
			<option value=<%=b.getBookId()%>><%=b.getTitle() %></option>
			<%}} %>
		</select>
		 </div>
		
        
		
		<div style="float: left;width: 54%;margin-top: 30px;margin-bottom: 50px;">
        <table class="table table-striped">
    		<tr>
    			<th> Books by this Genre </th>
    			<th> Delete </th>
    		</tr>		
    		<% for (Book b : genre.getBooks()) { %>
    		<tr>
    			<td>
    			
    			<% 	out.println(b.getTitle()); %>	 <br />
    			 </td>
    			 <td><button type="button"
					onclick="javascript:location.href='deleteGenreBook?genreId=<%=genre.getGenreId()%>&bookId=<%=b.getBookId() %>'"
					class="btn btn-danger btn-sm">Delete</button></td>
    		</tr>
				
		<%	}%>
         </table>
       </div>  
       <div >
       </div>
       <button type="submit" class="btn btn-primary btn-sm">Update Genre</button><br/><br/> 
	</form>
    </div>
    
    
