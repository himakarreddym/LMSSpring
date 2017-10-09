package com.gcit.lms;

import java.sql.SQLException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LibrarianController {
	
	@Autowired
	LibrarianService libService;
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);	
	
	@RequestMapping(value = "/librarian", method = RequestMethod.GET)
	public String gottoLibrarian(Locale locale, Model model,@RequestParam(value ="branchId",required=false) Integer branchId) {
		if(branchId == null) {
			branchId = 1;
		}
		LibraryBranch Branch = new LibraryBranch();
		Branch.setBranchId(branchId);
		BookCopies bookCopies = new BookCopies();
		bookCopies.setBookId(0);
		bookCopies.setBranchId(branchId);
		try {
			model.addAttribute("branchId",branchId);
			model.addAttribute("branches",libService.readLibraryBranch(null));
			model.addAttribute("bookcopies",libService.readBookCopies(bookCopies));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "librarian";
	}

	@RequestMapping(value = "/updatebranch", method = RequestMethod.GET)
	 public ModelAndView editbranch(Model model,@RequestParam(value ="branchId",required=false) Integer branchId) {
		LibraryBranch branch = new LibraryBranch();
		try {
			branch= libService.readbranchbyPK(branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return new ModelAndView("updatebranch", "LibraryBranch", branch);
	    }

	@RequestMapping(value = "/editBranchInf", method = RequestMethod.POST)
	public String editLibraryBranch1(@ModelAttribute("LibraryBranch")LibraryBranch branch,BindingResult result, ModelMap model) throws SQLException {
		
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
	   }
		libService.saveLibraryBranch(branch);
		model.addAttribute("branchId",branch.getBranchId());
		return "redirect:/librarian";
	}

	@RequestMapping(value = "/editbookcopies", method = RequestMethod.GET)
	 public ModelAndView editbookcopies(Model model,@RequestParam(value ="branchId",required=false) Integer branchId,
			 @RequestParam(value ="bookId",required=false) Integer bookId	) {
		BookCopies bookCopies = new BookCopies();
		try {
			bookCopies= libService.readBookCopiesbyPK(bookId,branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return new ModelAndView("editbookcopies", "BookCopies", bookCopies);
	    }
	@RequestMapping(value = "/editBookcopies", method = RequestMethod.POST)
	public String updatebookcopes(@ModelAttribute("BookCopies")BookCopies bookcopies,BindingResult result, ModelMap model) throws SQLException {
		
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
	   }
		libService.saveBookCopies(bookcopies);
		model.addAttribute("branchId",bookcopies.getBranchId());
		return "redirect:/librarian";
	}
	@RequestMapping(value = "/bookinf", method = RequestMethod.GET)
	 public String bookInformation(Model model,@RequestParam(value ="branchId",required=false) Integer branchId,
			 @RequestParam(value ="bookId",required=false) Integer bookId	) {
		try {
			model.addAttribute("Book",adminService.readBookByPK(bookId));
			model.addAttribute("bookId",bookId);
			model.addAttribute("branchId",branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return "bookinf";
	    }
}
