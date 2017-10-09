package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import java.sql.PreparedStatement;

@SuppressWarnings({  "rawtypes" })
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{


	public void saveBook(Book book) throws SQLException {
		template.update("INSERT INTO tbl_book (title,pubId) VALUES (?,?)", new Object[] { book.getTitle(),book.getPublisher().getPublisherId() });
	}
	
	public void saveBookAuthor(Book book) throws SQLException {
		for(Author a: book.getAuthors()){
			template.update("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] { book.getBookId(), a.getAuthorId()});
		}
	}
	public void saveBookGenre(Book book) throws SQLException {
		for(Genre g: book.getGenres()){
			template.update("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] {g.getGenreId() , book.getBookId()});
		}
	}
	
	public Integer saveBookID(Book book) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_book (title,pubId) VALUES (?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, book.getTitle());
                ps.setInt(2, book.getPublisher().getPublisherId());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBook(Book book) throws SQLException {
		template.update("UPDATE tbl_book SET title = ? , pubId = ? WHERE bookId = ?", new Object[] { book.getTitle(),book.getPublisher().getPublisherId() ,book.getBookId() });
	}
	public void updatebookPub(Book book) throws SQLException {
		template.update("UPDATE tbl_book SET pubId = ? WHERE bookId = ?", new Object[] { book.getPublisher().getPublisherId() ,book.getBookId() });
	}

	public void deleteBook(Book book) throws SQLException {
		template.update("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
	}
	public Integer getBooksCount() throws SQLException {
		return template.queryForObject ("SELECT count(*) as COUNT FROM tbl_book",Integer.class);
	}

	public List<Book> readAllBooks() throws SQLException {
		return template.query("SELECT * FROM tbl_book",this);
	}
	
	public List<Book> readBooksByTitle(String bookTitle) throws SQLException {
		bookTitle = "%"+bookTitle+"%";
		return template.query("SELECT * FROM tbl_book WHERE title like ?", new Object[]{bookTitle},this);
		
	}
	public Book readBookByPK(int bookId) throws SQLException{
		List<Book> books = template.query("SELECT * FROM tbl_book WHERE bookId = ?", new Object[]{bookId},this);
		if(books!=null){
			return books.get(0);
		}
		return null;
	}
	public List<Book> readAllBooksByAuthor(Author author) throws SQLException {
		return template.query("SELECT * FROM tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{author.getAuthorId()}, this);
	}
	public List<Book> readAllBooksByGenre(Genre genre) throws SQLException {
		return template.query("SELECT * FROM tbl_book where bookId IN (select bookId from tbl_book_genres where genre_id = ?)", new Object[]{genre.getGenreId()}, this);
	}
	public List<Book> readAllBooksByPublisher(Publisher publisher) throws SQLException {
		return template.query("SELECT * FROM tbl_book where pubId = ?", new Object[]{publisher.getPublisherId()}, this);
	}
	public List<Book> readBooksDAO(String bookTitle, Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		String sql = null;
		if(bookTitle !=null && !bookTitle.isEmpty()){
			bookTitle = "%"+bookTitle+"%";
			sql="SELECT * FROM tbl_book WHERE title like ?";
			sql+=pagenation();
			return template.query(sql, new Object[]{bookTitle},this);
		}else{
			sql="SELECT * FROM tbl_book";
			sql+=pagenation();
			return template.query(sql,this);
		}
		
	}
	public Book readAllBooksByCard(BookLoans bl) throws SQLException {
		List<Book> books= template.query("SELECT * FROM tbl_book where bookId = ?", new Object[]{bl.getBookId()}, this);
		if(books!=null){
			return books.get(0);
		}
		return null;
	}
	public Book readBooksByCopies(BookCopies bc) throws SQLException {
		List<Book> books= template.query("SELECT * FROM tbl_book where bookId = ?", new Object[]{bc.getBookId()}, this);
		if(books!=null){
			return books.get(0);
		}
		return null;
	}
	@Override
	public List extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(pub);
			books.add(b);
		}
		return books;
	}
	public String pagenation() {
		if(getPageNo()!=null){
			Integer index = (getPageNo() -1) * getPageSize();
			return " LIMIT "+index+","+getPageSize();
		}
		return null;
		}
}
