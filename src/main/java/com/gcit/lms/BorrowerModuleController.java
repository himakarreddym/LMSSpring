package com.gcit.lms;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BorrowerModuleController {

	@Autowired
	BorrowerService borService;
	@Autowired
	AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(BorrowerModuleController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(Locale locale, Model model) {
		logger.info("Welcome to home method", locale);
		return "welcome";
	}

	@RequestMapping(value = "/borrower", method = RequestMethod.GET)
	public ModelAndView GoTOBorrowerhome(Locale locale, Model model) {
		return new ModelAndView("borrower", "Borrower", new Borrower());
	}

	@RequestMapping(value = "/checkcard", method = RequestMethod.POST)
	public String checkCardNo(@ModelAttribute("Borrower") Borrower borrower, BindingResult result, ModelMap model)
			throws SQLException {
		if (result.hasErrors()) {
			logger.info("error", result.getFieldError());
		}
		Borrower borr = new Borrower();
		borr = borService.readBorrowerByPK(borrower.getCardNo());
		if (borr != null) {
			model.addAttribute("cardNo", borrower.getCardNo());
			return "redirect:/borrowermenu";
		} else {
			return "redirect:/borrower";
		}
	}

	@RequestMapping(value = "/borrowermenu", method = RequestMethod.GET)
	public ModelAndView gottoLibrarian(Locale locale, Model model,
			@RequestParam(value = "cardNo", required = false) Integer cardNo,
			@RequestParam(value = "branchId", required = false) Integer branchId) {
		if (branchId == null) {
			branchId = 1;
		}
		LibraryBranch Branch = new LibraryBranch();
		Branch.setBranchId(branchId);
		BookCopies bookCopies = new BookCopies();
		bookCopies.setBookId(0);
		bookCopies.setBranchId(branchId);
		BookLoans bookloans = new BookLoans();
		bookloans.setBranchId(branchId);
		bookloans.setCardNo(cardNo);
		try {
			model.addAttribute("branchId", branchId);
			model.addAttribute("cardNo", cardNo);
			model.addAttribute("branches", borService.readLibraryBranch(null));
			model.addAttribute("bookcopies", borService.readBookCopies(bookCopies));
			model.addAttribute("bookloans", borService.readBookLoansBycard(bookloans));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("borrowermenu", "BookLoans", bookloans);
	}

	@RequestMapping(value = "/checkbook", method = RequestMethod.POST)
	public String checkBook(@ModelAttribute("BookLoans") BookLoans bookloans, BindingResult result, ModelMap model)
			throws SQLException {

		if (result.hasErrors()) {
			logger.info("error", result.getFieldError());
		}

		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		java.sql.Timestamp date1 = new java.sql.Timestamp(
				new java.util.Date().getTime() + 7l * 24l * 60l * 60l * 1000l);
		bookloans.setDateOut(date);
		bookloans.setDueDate(date1);
		bookloans.setDateIn(null);
		BookCopies bc = new BookCopies();
		bc.setBookId(bookloans.getBookId());
		bc.setBranchId(bookloans.getBranchId());
		try {
			List<BookCopies> bookcopies = borService.readBookCopies(bc);
			BookCopies bc1 = bookcopies.get(0);
			bc.setCopies(bc1.getCopies() - 1);
			borService.saveBookLoans(bookloans);
			borService.saveBookCopies(bc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("branchId", bookloans.getBranchId());
		model.addAttribute("cardNo", bookloans.getCardNo());
		return "redirect:/borrowermenu";
	}

	@RequestMapping(value = "/checkInBook", method = RequestMethod.POST)
	public String checkInBook(@ModelAttribute("BookLoans") BookLoans bookloans,
			@RequestParam(value = "dateout", required = false) String dateOut,
			@RequestParam(value = "duedate", required = false) String dueDate, BindingResult result, ModelMap model)
			throws SQLException {

		if (result.hasErrors()) {
			logger.info("error" + result.toString());
		}
		logger.info("dateOut" + dateOut + "dueDate" + dueDate);
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		bookloans.setDateIn(date);
		bookloans.setDateOut(Timestamp.valueOf(dateOut));
		bookloans.setDueDate(Timestamp.valueOf(dueDate));
		BookCopies bc = new BookCopies();
		bc.setBookId(bookloans.getBookId());
		bc.setBranchId(bookloans.getBranchId());
		try {
			List<BookCopies> bookcopies = borService.readBookCopies(bc);
			BookCopies bc1 = bookcopies.get(0);
			bc.setCopies(bc1.getCopies() + 1);
			borService.updateBookLoans(bookloans);
			borService.saveBookCopies(bc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("branchId", bookloans.getBranchId());
		model.addAttribute("cardNo", bookloans.getCardNo());
		return "redirect:/borrowermenu";
	}

	@RequestMapping(value = "/OverrideDuedate", method = RequestMethod.GET)
	public String gottoOverrideDuedate(Locale locale, Model model,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "statusMessage", required = false) String message) {
		Integer totalCount = -1;
		if (pageNo == null) {
			pageNo = 1;
		}
		List<BookLoans> bookloans = new ArrayList<BookLoans>();
		try {
			totalCount = adminService.getbookLoansCount();
			int numOfPages = 0;
			if (totalCount % 4 > 0) {
				numOfPages = totalCount / 4 + 1;
			} else {
				numOfPages = totalCount / 4;
			}
			bookloans = adminService.readBookLoansPage(pageNo);
			model.addAttribute("BookLoans", bookloans);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("numOfPages", numOfPages);
			model.addAttribute("pageNo", pageNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("statusMessage",message);
		return "OverrideDuedate";
	}

	@RequestMapping(value = "/override", method = RequestMethod.POST)
	public ModelAndView gottoOverridepage(Locale locale, Model model,
			@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam(value = "branchId", required = false) Integer branchId,
			@RequestParam(value = "cardNo", required = false) Integer cardNo,
			@RequestParam(value = "dateOut", required = false) String dateOut) {
		BookLoans bookloans = new BookLoans();
		bookloans.setBookId(bookId);
		bookloans.setBranchId(branchId);
		bookloans.setCardNo(cardNo);
		bookloans.setDateOut(Timestamp.valueOf(dateOut));
		return new ModelAndView("overidedate", "BookLoans", bookloans);
	}

	@RequestMapping(value = "/editdueDate", method = RequestMethod.POST)
	public String OverrideBook(@ModelAttribute("BookLoans") BookLoans bookloans,
			@RequestParam(value = "dateout", required = false) String dateout,
			@RequestParam(value = "newdueDate", required = false) String newdueDate, BindingResult result,
			ModelMap model) throws SQLException {
		String message = null;
		if (result.hasErrors()) {
			logger.info("error" + result.toString());
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(newdueDate);
			bookloans.setDateOut(Timestamp.valueOf(dateout));
			Timestamp newdueDate1 = (Timestamp) new java.sql.Timestamp(date1.getTime());
			bookloans.setDueDate(newdueDate1);
			adminService.updatedueDate(bookloans);
			message = "<div class=\"alert alert-success alert-dismissable custom-alert\" role=\"alert\"><strong>Success!!!</strong>Due date updated sucessfully!!</div>";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("statusMessage",message);
		return "redirect:/OverrideDuedate";
	}

}
