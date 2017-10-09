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

import com.gcit.lms.entity.Borrower;
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BorrowerController {
	
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/borrower1", method = RequestMethod.GET)
	public String gottoBorrower(Locale locale, Model model) {
		return "borrower1";
	}
	
	@RequestMapping(value = "/addborrower", method = RequestMethod.GET)
		 public ModelAndView showForm(Model model) {
		        return new ModelAndView("addborrower", "Borrower", new Borrower());
		    }
	
	
	@RequestMapping(value = "/addborrower1", method = RequestMethod.POST)
	public String addBorrower(@ModelAttribute("Borrower")Borrower borrower,BindingResult result, ModelMap model) throws SQLException {
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
        }
		adminService.saveBorrower(borrower);
		return "redirect:/viewborrowers";
	}
	 
	@RequestMapping(value = "/viewborrowers", method = RequestMethod.GET)
	public String gottoviewBorrowers(Locale locale, Model model,@RequestParam(value ="pageNo",required=false) Integer pageNo) {
		Integer totalCount =-1;
		if(pageNo == null) {
			pageNo=1;
		}
			try {
				totalCount = adminService.getBorrowersCount();
				int numOfPages = 0;
				if (totalCount % 4 > 0) {
					numOfPages = totalCount / 4 + 1;
				} else {
					numOfPages = totalCount / 4;
				}
				model.addAttribute("borrowers", adminService.readBorrowers(null, pageNo));
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("numOfPages", numOfPages);
				model.addAttribute("pageNo",pageNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "viewborrowers";
	}
	
	@RequestMapping(value = "/editborrower", method = RequestMethod.GET)
	 public ModelAndView editBorrower(Model model,@RequestParam(value ="cardNo",required=false) Integer cardNo) {
		Borrower borrower = new Borrower();
		try {
			borrower= adminService.readBorrowerByPK(cardNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return new ModelAndView("editborrower", "Borrower", borrower);
	    }


	@RequestMapping(value = "/editborrower1", method = RequestMethod.POST)
	public String editBorrower1(@ModelAttribute("Borrower")Borrower borrower,BindingResult result, ModelMap model) throws SQLException {
		
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
	   }
		adminService.saveBorrower(borrower);
		return "redirect:/viewborrowers";
	}
	@RequestMapping(value = "/deleteBorrower", method = RequestMethod.GET)
	 public String deleteBorrower(Model model,@RequestParam(value ="cardNo",required=false) Integer cardNo) {
		Borrower borrower = new Borrower();
		try {
			borrower.setCardNo(cardNo);
			adminService.deleteBorrower(borrower);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/viewborrowers";
	    }
}
