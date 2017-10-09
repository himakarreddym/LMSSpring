package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;

	public class AdminService {
		
		@Autowired
		AuthorDAO adao;
		@Autowired
		BookDAO bdao;
		@Autowired
		BookLoansDAO bldao;
		@Autowired
		BorrowerDAO brdao;
		@Autowired
		GenreDAO gdao;
		@Autowired
		LibraryBranchDAO lbdao;
		@Autowired
		PublisherDAO pdao;
		@Autowired
		BookCopiesDAO bcdao;
		
		
		/**
		 * Author services
		 */
		
		@Transactional
		public void saveAuthor(Author author) throws SQLException{
				if(author.getAuthorId()!=null){
					adao.updateAuthor(author);
					if(author.getBooks() != null && author.getBooks().size() > 0)
					adao.saveBookAuthor(author);
				}else{
					author.setAuthorId(adao.saveAuthorWithID(author));
					adao.saveBookAuthor(author);
				}
		}
		@Transactional
		public void deleteAuthor(Author author) throws SQLException{
			adao.deleteAuthor(author);
	}
		
		public List<Author> getAuthors(String[] authorIds) throws SQLException
		{
				List<Author> authors = new ArrayList<>();
				for(String authorId: authorIds)
				{
					authors.add(adao.readAuthorByPK(Integer.parseInt(authorId)));
				}
				return authors;
		}
		
		public List<Author> readAuthors() throws SQLException{
				return adao.readAllAuthors();
		}
		
		public List<Author> readAuthors(String searchString, Integer pageNo) throws SQLException{
			List<Author> authors = adao.readAuthors(searchString, pageNo);
			for(Author a: authors){
				a.setBooks(bdao.readAllBooksByAuthor(a));
			}
			return authors;
			} 
		public Author readAuthorByPK(Integer authorId) throws SQLException{
				Author author= adao.readAuthorByPK(authorId);
				author.setBooks(bdao.readAllBooksByAuthor(author));
				return author;
			} 
		public Integer getAuthorsCount(String authorName) throws SQLException{
				return adao.getAuthorsCount(authorName);
			}
		
		/**
		 * Book services
		 */	
		@Transactional
		public void saveBook(Book book) throws SQLException {
				if (book.getBookId() != null) {
					bdao.updateBook(book);
					if (book.getAuthors() != null && book.getAuthors().size() > 0)
						bdao.saveBookAuthor(book);
					if (book.getGenres() != null && book.getGenres().size() > 0)
						bdao.saveBookGenre(book);
				} else {
					book.setBookId(bdao.saveBookID(book));
					bdao.saveBookAuthor(book);
					bdao.saveBookGenre(book);
				}
		}
		@Transactional
		public void deleteBook(Book book) throws SQLException {
				bdao.deleteBook(book);
		}
	
		public List<Book> getBooks(String[] bookIds) throws SQLException {
				List<Book> books = new ArrayList<>();
				for (String bookId : bookIds) {
					books.add(bdao.readBookByPK(Integer.parseInt(bookId)));
				}
				return books;
		}
	
		public List<Book> readBooks() throws SQLException {
				return bdao.readAllBooks();
			} 
		
		public List<Book> readBooks(String searchString, Integer pageNo) throws SQLException {
			List<Book> books = bdao.readBooksDAO(searchString, pageNo);
			for(Book b: books){
				b.setAuthors(adao.readAuthorsByBook(b));
				b.setGenres(gdao.readGenresByBook(b));
				b.setPublisher(pdao.readPublisherByPK(b.getPublisher().getPublisherId()));
			}
			return books;
			} 
	
		public Book readBookByPK(Integer bookId) throws SQLException {
				Book book= bdao.readBookByPK(bookId);
				book.setAuthors(adao.readAuthorsByBook(book));
				book.setGenres(gdao.readGenresByBook(book));
				book.setPublisher(pdao.readPublisherByPK(book.getPublisher().getPublisherId()));
				return book;
			} 
		public Integer getBooksCount() throws SQLException{
			return bdao.getBooksCount();
		} 
		
		/**
		 * Genre
		 */
		@Transactional
		public void saveGenre(Genre genre) throws SQLException{
				if(genre.getGenreId()!=null){
					gdao.updateGenre(genre);
					if(genre.getBooks() != null && genre.getBooks().size() > 0)
					gdao.saveBookGenre(genre);
				}else{
					genre.setGenreId(gdao.saveGenreWithID(genre));
					gdao.saveBookGenre(genre);
				}
		}
		@Transactional
		public void deleteGenre(Genre genre) throws SQLException{
			gdao.deleteGenre(genre);
		}
	
		public List<Genre> readGenre(String searchString,Integer pageNo) throws SQLException{
				List<Genre> genres = gdao.readGenres(searchString,pageNo);
				for(Genre g : genres) {
					g.setBooks(bdao.readAllBooksByGenre(g));
				}
				return genres;
			} 
		
		public List<Genre> getGenres(String[] genreIds) throws SQLException
			{
					List<Genre> genres = new ArrayList<>();
					for(String genreId: genreIds)
					{
						genres.add(gdao.readGenreByPK(Integer.parseInt(genreId)));
					}
					return genres;
			}
		public List<Genre> readGenres() throws SQLException
			{
			List<Genre> genres = gdao.readGenres(null);
			for(Genre g : genres) {
				g.setBooks(bdao.readAllBooksByGenre(g));
				}
			return genres;
			} 
		public Genre readGenreByPK(Integer genreId) throws SQLException{
			Genre genre= gdao.readGenreByPK(genreId);
			genre.setBooks(bdao.readAllBooksByGenre(genre));
			return genre;
		} 
		public Integer getGenresCount() throws SQLException{
			return gdao.getGenresCount();
		} 
		
		/**
		 * Publisher
		 * 
		 */
		@Transactional
		public void savePublisher(Publisher publisher) throws SQLException{
			if(publisher.getPublisherId()!=null){
				pdao.updatePublisher(publisher);
			}else{
				pdao.savePublisher(publisher);
			}
		}
		@Transactional
		public void deletePublisher(Publisher publisher) throws SQLException{
				pdao.deletePublisher(publisher);
		}
	
		public List<Publisher> readPublishers() throws SQLException{
				return pdao.readPublishers(null);
			} 
		
		public Publisher readPublisherByPK(Integer pubId) throws SQLException{
				return pdao.readPublisherByPK(pubId);
			} 
	
		public List<Publisher> readPublishers(String searchString) throws SQLException{
			List<Publisher> pubList = pdao.readPublishers(searchString);
			for(Publisher pub : pubList ) {
				pub.setBooks(bdao.readAllBooksByPublisher(pub));
			}
			return pubList;
			} 
		public List<Publisher> readPublisher(String searchString,Integer pageNo) throws SQLException{
			List<Publisher> pubList =pdao.readPublishers(searchString,pageNo);
			for(Publisher pub : pubList ) {
				pub.setBooks(bdao.readAllBooksByPublisher(pub));
			}
			return pubList;
		} 
		public Integer getPublishersCount() throws SQLException{
			return pdao.getPublishersCount();
		} 
		/**
		 * Borrower
		 */
		@Transactional
		public void saveBorrower(Borrower borrower) throws SQLException{
				if(borrower.getCardNo()!=null){
					brdao.updateBorrower(borrower);
				}else{
					brdao.saveBorrower(borrower);
				}
		}
		@Transactional
		public void deleteBorrower(Borrower borrower) throws SQLException{
				brdao.deleteBorrower(borrower);
		}
	
		public List<Borrower> readBorrower(String searchString) throws SQLException{
			List<Borrower> borrowers=brdao.readBorrowers(searchString);
				for(Borrower br: borrowers) {
					br.setBookloans(bldao.readAllBooksLoansBycard(br));
				}
					return borrowers;
			} 
		
		public List<Borrower> readBorrowers(String searchString,Integer pageNo) throws SQLException{
			List<Borrower> borrowers=brdao.readBorrowers1(searchString,pageNo);
			for(Borrower br: borrowers) {
				br.setBookloans(bldao.readAllBooksLoansBycard(br));
			}
				return borrowers;
			} 
	
		public Borrower readBorrowerByPK(Integer cardNo) throws SQLException{
				return brdao.readBorrowerByPK(cardNo);
		} 
	
		public Integer getBorrowersCount() throws SQLException{
			return brdao.getBorrowersCount();
		} 

		/**
		 * Library_Branches 
		 */
		@Transactional
		public int saveLibraryBranch(LibraryBranch branch) throws SQLException{
			int branchId = -1;
				if(branch.getBranchId()!=null){
					lbdao.updateLibraryBranches(branch);
				}else{
					branchId =lbdao.saveLibraryBranchesWithID(branch);
				}
			return branchId;
		}
		@Transactional
		public void deleteLibraryBranch(LibraryBranch borrower) throws SQLException{
				lbdao.deleteLibraryBranches(borrower);
		}
		public List<LibraryBranch> readLibraryBranch(String searchString) throws SQLException{
			List<LibraryBranch> branches= lbdao.readBranchs(searchString);
			for(LibraryBranch lb: branches) {
				lb.setBookcopies(bcdao.readAllBookCopiesByBranch(lb));
			}
			return branches;
		} 
		public LibraryBranch readBranchByPK(Integer branchId) throws SQLException{
				return lbdao.readbranchByPK(branchId);
		} 
		public List<LibraryBranch> readBranch(String searchString,Integer pageNo) throws SQLException{
			List<LibraryBranch> branches = lbdao.readBranch(searchString,pageNo);
			for(LibraryBranch lb: branches) {
				lb.setBookcopies(bcdao.readAllBookCopiesByBranch(lb));
			}
			return branches;
		} 
		public Integer getbarnchesCount() throws SQLException{
			return lbdao.getbarnchesCount();
		} 
		/**
		 * Book Copies
		 */
		@Transactional
		public void savebookCopies(BookCopies bookCopies) throws SQLException{
			bcdao.saveBookCopies(bookCopies);
		}
		public List<BookCopies> readallBookCopies() throws SQLException{
			List<BookCopies> bookcopies = bcdao.readBookCopies(null);
			for(BookCopies bc : bookcopies) {
				bc.setBook(bdao.readBooksByCopies(bc));
				bc.setBranch(lbdao.readBranchBycopies(bc));
			}
			return bookcopies;
		} 
		
		/**
		 * Book Loans
		 */
		@Transactional
		public void updatedueDate(BookLoans bookloans) throws SQLException{
			bldao.updatedueDate(bookloans);
		}
		public List<BookLoans> readallBookLoans() throws SQLException{
			List<BookLoans> bookLoans = bldao.readAllbookLoans();
			for(BookLoans bl : bookLoans) {
				bl.setBook(bdao.readAllBooksByCard(bl));
				bl.setBorrower(brdao.readAllBorrowersByCard(bl));
				bl.setBranch(lbdao.readBranchByCard(bl));
			}
			return bookLoans;
		} 
		public List<BookLoans> readBookLoans1() throws SQLException{	
			List<BookLoans> bookLoans = bldao.readBookLoans1();
			for(BookLoans bl : bookLoans) {
				bl.setBook(bdao.readAllBooksByCard(bl));
				bl.setBorrower(brdao.readAllBorrowersByCard(bl));
				bl.setBranch(lbdao.readBranchByCard(bl));
			}
			return bookLoans;
		} 
		public List<BookLoans> readBookLoansPage(Integer pageNo) throws SQLException{
			List<BookLoans> bookLoans =  bldao.readBookLoansPage(pageNo);
			for(BookLoans bl : bookLoans) {
				bl.setBook(bdao.readAllBooksByCard(bl));
				bl.setBorrower(brdao.readAllBorrowersByCard(bl));
				bl.setBranch(lbdao.readBranchByCard(bl));
			}
			return bookLoans;
		} 
		public Integer getbookLoansCount() throws SQLException{
			return bldao.getbookLoansCount();
		} 
		/**
		 * Mixed
		 */
		@Transactional
		public void upadatebookPublisher(Book book) throws SQLException{
				if(book.getBookId()!=null){
					bdao.updatebookPub(book);
				}
		}	
		@Transactional
		public void deleteBookAuthor(Author author,int bookId) throws SQLException{
				adao.deleteBookAuthor(author,bookId);
		}
		@Transactional
		public void deleteBookGenre(Genre genre,int bookId) throws SQLException{
				gdao.deleteBookGenre(genre,bookId);
		}
}
