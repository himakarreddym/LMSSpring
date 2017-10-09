package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;


@SuppressWarnings({ "rawtypes" })
public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>>{


	public void saveBookCopies(BookCopies bookcopies) throws SQLException {
		template.update("INSERT INTO tbl_book_copies (bookId,branchId,noOfCopies) VALUES (?,?,?)", new Object[] { bookcopies.getBookId(),bookcopies.getBranchId(),bookcopies.getCopies() });
	}
	
//	public void saveBookBookCopies(BookCopies bookcopies) throws SQLException {
//		for(Book b: bookcopies.getBooks()){
//			save("INSERT INTO tbl_book VALUES (?, ?,?)", new Object[] { b.getBookId(), b.getTitle(),bookcopies.getPublisherId()});
//		}
//	}
	
	public Integer saveBookCopiesWithID(BookCopies bookcopies) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_book_copies (bookId,branchId,noOfCopies) VALUES (?,?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps =   (PreparedStatement) connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, bookcopies.getBookId());
                ps.setInt(2, bookcopies.getBranchId());
                ps.setInt(3, bookcopies.getCopies());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBookCopies(BookCopies bookcopies) throws SQLException {
		template.update("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?",
				new Object[] { bookcopies.getCopies(),bookcopies.getBookId(),bookcopies.getBranchId() });
	}

	public void deleteBookCopies(BookCopies bookcopies) throws SQLException {
		template.update("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] { bookcopies.getBookId(),bookcopies.getBranchId() });
	}
	
	

	public List<BookCopies> readBookCopies(BookCopies bookcopies) throws SQLException {
		if(bookcopies.getBookId() !=0 && bookcopies.getBranchId()!=0 ){
			return template.query("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[]{bookcopies.getBookId(),bookcopies.getBranchId()},this);
		}
		else if(bookcopies.getBookId()!=0 && bookcopies.getBranchId() ==0) {
			return template.query("SELECT * FROM tbl_book_copies WHERE bookId = ? ", new Object[]{bookcopies.getBookId()},this);
		}
		else if(bookcopies.getBookId() ==0 && bookcopies.getBranchId() !=0) {
			return template.query("SELECT * FROM tbl_book_copies WHERE branchId = ? ", new Object[]{bookcopies.getBranchId()},this);
		}
		else
		{
			return template.query("SELECT * FROM tbl_book_copies",this);
		}
		
	}
	public List<BookCopies> readBookCopiesbyCond(BookCopies bookcopies) throws SQLException {
		return template.query("SELECT * FROM tbl_book_copies WHERE branchId = ? AND noOfCopies > 0 ", new Object[]{bookcopies.getBranchId()},this);
	}
	
	public List<BookCopies> readAllBookCopiesByBranch(LibraryBranch lib) throws SQLException {
		return template.query("SELECT * FROM tbl_book_copies where branchId IN (select branchId from library.tbl_book_copies where branchId = ?)", new Object[]{lib.getBranchId()}, this);
	}
	
	public BookCopies readBookCopiesByPK(Integer bookId, Integer branchId) throws SQLException {
		List<BookCopies> bookCopies = template.query("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[]{bookId,branchId},this);
		if(bookCopies!=null){
			return bookCopies.get(0);
		}
		return null;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookcopiess = new ArrayList<>();
		while(rs.next()){
			BookCopies a = new BookCopies();
			a.setBookId(rs.getInt("bookId"));
			a.setBranchId(rs.getInt("branchId"));
			a.setCopies(rs.getInt("noOfCopies"));
			bookcopiess.add(a);
		}
		
		return bookcopiess;
	}

}
