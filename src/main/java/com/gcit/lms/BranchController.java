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

import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BranchController {
	
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(BranchController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome to home method", locale);
//		return "welcome";
//	}
	
	
	@RequestMapping(value = "/Librarybranch", method = RequestMethod.GET)
	public String gottoLibraryBranch(Locale locale, Model model) {
		return "Librarybranch";
	}
	
	@RequestMapping(value = "/addbranch", method = RequestMethod.GET)
		 public ModelAndView showForm(Model model) {
			try {
				model.addAttribute("books", adminService.readBooks());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        return new ModelAndView("addbranch", "LibraryBranch", new LibraryBranch());
		    }
	
	
	@RequestMapping(value = "/addbranch1", method = RequestMethod.POST)
	public String addbranch(@ModelAttribute("LibraryBranch")LibraryBranch branch,BindingResult result, ModelMap model) throws SQLException {
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
        }
		int branchid1 =adminService.saveLibraryBranch(branch);
		return "redirect:/viewbranches";
	}
	 
	@RequestMapping(value = "/viewbranches", method = RequestMethod.GET)
	public String gottoviewbranchs(Locale locale, Model model,@RequestParam(value ="pageNo",required=false) Integer pageNo) {
		Integer totalCount =-1;
		if(pageNo == null) {
			pageNo=1;
		}
			try {
				totalCount = adminService.getbarnchesCount();;
				int numOfPages = 0;
				if (totalCount % 4 > 0) {
					numOfPages = totalCount / 4 + 1;
				} else {
					numOfPages = totalCount / 4;
				}
				model.addAttribute("branches", adminService.readBranch(null, pageNo));
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("numOfPages", numOfPages);
				model.addAttribute("pageNo",pageNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "viewbranches";
	}
	
	@RequestMapping(value = "/editbranch", method = RequestMethod.GET)
	 public ModelAndView editbranch(Model model,@RequestParam(value ="branchId",required=false) Integer branchId) {
		LibraryBranch branch = new LibraryBranch();
		try {
			branch= adminService.readBranchByPK(branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return new ModelAndView("editbranch", "LibraryBranch", branch);
	    }


	@RequestMapping(value = "/editbranch1", method = RequestMethod.POST)
	public String editLibraryBranch1(@ModelAttribute("LibraryBranch")LibraryBranch branch,BindingResult result, ModelMap model) throws SQLException {
		
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
	   }
		adminService.saveLibraryBranch(branch);
		return "redirect:/viewbranches";
	}
	@RequestMapping(value = "/deletebranch", method = RequestMethod.GET)
	 public String deleteBorrower(Model model,@RequestParam(value ="branchId",required=false) Integer branchId) {
		LibraryBranch branch = new LibraryBranch();
		try {
			branch.setBranchId(branchId);
			adminService.deleteLibraryBranch(branch);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/viewbranches";
	    }
}
