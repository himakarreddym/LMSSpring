package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

public class LibrarianService {

	@Autowired
	BookDAO bdao;
	@Autowired
	LibraryBranchDAO lbdao;
	@Autowired
	BookCopiesDAO bcdao;

	public List<LibraryBranch> readLibraryBranch(String searchString) throws SQLException{
		List<LibraryBranch> branches= lbdao.readBranchs(searchString);
		for(LibraryBranch lb: branches) {
			lb.setBookcopies(bcdao.readAllBookCopiesByBranch(lb));
		}
		return branches;
	}
	@Transactional
	public void saveLibraryBranch(LibraryBranch branches) throws SQLException{
			if(branches.getBranchId()!=null){
				lbdao.updateLibraryBranches(branches);
			}else{
				lbdao.saveLibraryBranches(branches);
			}
	}
	public List<BookCopies> readBookCopies(BookCopies bookcopies) throws SQLException{
		List<BookCopies> bookCopies= bcdao.readBookCopies(bookcopies);
		for(BookCopies bc : bookCopies) {
			bc.setBook(bdao.readBooksByCopies(bc));
			bc.setBranch(lbdao.readBranchBycopies(bc));
		}
		return bookCopies;
		}  
	public BookCopies readBookCopiesbyPK(int bookId,int branchId) throws SQLException{
			return bcdao.readBookCopiesByPK(bookId, branchId);
		} 
	public LibraryBranch readbranchbyPK(int branchId) throws SQLException{
			return lbdao.readbranchByPK(branchId);
		} 
	@Transactional
	public void saveBookCopies(BookCopies bookcopies) throws SQLException{
			if(bookcopies.getBranchId()!=null && bookcopies.getBookId() != null){
				bcdao.updateBookCopies(bookcopies);
			}else{
				bcdao.saveBookCopies(bookcopies);
			}
	}	
}
