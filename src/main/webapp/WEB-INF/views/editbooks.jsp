<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%AdminService service = new AdminService();
Book book = service.readBookByPK(Integer.parseInt(request.getParameter("bookId")));
List<Author> authors = service.readAuthors();
List<Author> authorBooks = book.getAuthors();
List<Publisher> publishers =service.readPublishers();
Publisher bookPublisher = book.getPublisher();
List<Genre> genres =service.readGenres();
List<Genre> bookGenres = book.getGenres();
%>

<div class="container">
    
	<form method="post" action="editBook">
		${statusMessage}
	<br/>Enter new Book Title : <input type="text" name="bookName" value="<%=book.getTitle()%>"><br />
		<input type="hidden" name="bookId" value="<%=book.getBookId()%>"><br/>
		<div style="width:100%;float:left;">
		
        	<div class="form-group" align="center" style="width:50%;display:inline-block;float:left;" >
		<label for="sel1">Select new Books from list Below: </label>
		<select class="form-group" id="sel1" multiple="multiple" size="10" name="authorIds" style="width:80%;">
			<%for(Author a: authors) {
			if(! authorBooks.contains(a)) {
			%>
			<option value=<%=a.getAuthorId()%>><%=a.getAuthorName() %></option>
			<% }} %>
		</select>
		 </div>
		 
		 <div align="center" style="width:50%;display:inline-block;float:left;">
        <table class="table table-striped">
    		<tr>
    			<th> Books Written by the Author </th>
    			<th> Delete </th>
    		</tr>		
    		<% for (Author b : book.getAuthors()) { %>
    		<tr>
    			<td>
    			
    			<% 	out.println(b.getAuthorName()); %>	 <br />
    			 </td>
    			 <td><button type="button"
					onclick="javascript:location.href='deleteBookAuthor?authorId=<%=b.getAuthorId()%>&bookId=<%=book.getBookId() %>'"
					class="btn btn-danger btn-sm">Delete</button></td>
    		</tr>
				
		<%	}%>
         </table>
         
         </div>
         </div>
         <br/>
         <div style="width:100%;float:left;margin-top:40px;">
          <div class="form-group" align="center" style="width:50%;display:inline-block;float:left;">
		<label for="sel1">Select new Genres from list Below: </label>
		<select class="form-group" id="sel1" multiple="multiple" size="10" name="genreIds" style="width:80%;">
			<%for(Genre g: genres) {
			if(! bookGenres.contains(g)) {
			%>
			<option value=<%=g.getGenreId()%>><%=g.getGenreName()%></option>
			<% }} %>
		</select>
		 </div>
		 
		 <div align="center" style="width:50%;display:inline-block;float:left;">
        <table class="table table-striped">
    		<tr>
    			<th> Available Genres for the Book </th>
    			<th> Delete </th>
    		</tr>		
    		<% for (Genre g : book.getGenres()) { %>
    		<tr>
    			<td>
    			
    			<% 	out.println(g.getGenreName()); %>	 <br />
    			 </td>
    			 <td><button type="button"
					onclick="javascript:location.href='deleteGenreBook1?genreId=<%=g.getGenreId()%>&bookId=<%=book.getBookId() %>'"
					class="btn btn-danger btn-sm">Delete</button></td>
    		</tr>
				
		<%	}%>
         </table>
         
         </div>
         </div>
         
         <div style="width:100%;float:left;margin-top:40px;">
       <div class="form-group" align="center" style="width:50%;display:inline-block;float:left;">
		<label for="sel1">Select new Publisher from list Below: </label>
		<select class="form-group" id="sel1" name="publisherId" style="width:80%">
			<%for(Publisher p: publishers) {
			
			%>
				<option value=<%=p.getPublisherId()%>
				<%if(book.getPublisher() != null && book.getPublisher().getPublisherId() == p.getPublisherId()){
					out.print(" selected");
				} %>
				
				
				><%=p.getPublisherName()%></option>	 
			<%  }%>
		</select>
		 </div>
		 
		 <div align="center" style="width:50%;display:inline-block;float:left;">
        <table class="table table-striped">
    		<tr>
    			<th> Current Publisher for the Book </th>
    			<th> Delete </th>
    		</tr>	
    		<%if(book.getPublisher() != null) { 	%>
    		<tr>
    			<td>
    			<% 	out.println(book.getPublisher().getPublisherName()); %>	 <br />
    			 </td>
    			 <td><button type="button"
					onclick="javascript:location.href='deletebookPublisher?bookId=<%=book.getBookId() %>'"
					class="btn btn-danger btn-sm">Delete</button></td>
    		</tr>
			<%} %>
         </table>
         
         </div>
        </div> 
        <div style="text-align:center;"><button type="submit" class="btn btn-primary btn-md">Update Author</button><br/><br/></div>
		
		<br/>
            
	</form>
    </div>
    