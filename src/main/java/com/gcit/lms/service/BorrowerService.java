package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {

	@Autowired
	BookLoansDAO bldao;
	@Autowired
	BorrowerDAO brdao;
	@Autowired
	LibraryBranchDAO lbdao;
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BookDAO bdao;

	public List<LibraryBranch> readLibraryBranch(String searchString) throws SQLException{
			return lbdao.readBranchs(searchString);
	}
	
	public List<BookLoans> readBookLoans(BookLoans bookloans) throws SQLException{
			return bldao.readBookLoans(bookloans);
	} 
	public List<BookLoans> readBookLoansBycard(BookLoans bookloans) throws SQLException{
		List<BookLoans> bookLoans= bldao.readBookLoansBycard(bookloans);
		for(BookLoans bl : bookLoans) {
			bl.setBook(bdao.readAllBooksByCard(bl));
			bl.setBranch(lbdao.readBranchByCard(bl));
		}
		return bookLoans;
	} 
	@Transactional
	public void saveBookLoans(BookLoans bookloans) throws SQLException{
				bldao.saveBookLoans(bookloans);
	}
	@Transactional
	public void updateBookLoans(BookLoans bookloans) throws SQLException{
				bldao.updateBookLoans(bookloans);
	}
	public List<BookCopies> readBookCopies(BookCopies bookcopies) throws SQLException{
		List<BookCopies> bookCopies= bcdao.readBookCopies(bookcopies);
		for(BookCopies bc : bookCopies) {
			bc.setBook(bdao.readBooksByCopies(bc));
			bc.setBranch(lbdao.readBranchBycopies(bc));
		}
		return bookCopies;
		} 
	public List<BookCopies> readBookCopiesbyCond(BookCopies bookcopies) throws SQLException{
			return bcdao.readBookCopiesbyCond(bookcopies);
		} 
	@Transactional
	public void saveBookCopies(BookCopies bookcopies) throws SQLException{
			if(bookcopies.getBranchId()!=null && bookcopies.getBookId() != null){
				bcdao.updateBookCopies(bookcopies);
			}else{
				bcdao.saveBookCopies(bookcopies);
			}
	}
	public Borrower readBorrowerByPK(int cardNo) throws SQLException{
			return brdao.readBorrowerByPK(cardNo);
		}
	}
