package ua.com.itquiz.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.services.AdminService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends CommonController {

    @Autowired
    protected AdminService adminService;

    @RequestMapping(value = "/myaccount", method = RequestMethod.GET)
    @Override
    protected String showMyAccount(HttpSession session, Model model) {
	return "admin/" + super.showMyAccount(session, model);
    }

    @RequestMapping(value = "/myaccount", method = RequestMethod.POST)
    public String editPersonalInformation(@ModelAttribute("personalInfoForm") AccountInfoForm accountInfoForm,
	    Model model, HttpSession session) {
	try {
	    model.addAttribute("message", super.editPersonalData(session, accountInfoForm));
	    return "admin/myaccount";
	} catch (InvalidUserInputException ex) {
	    model.addAttribute("errorMessage", ex.getMessage());
	    return "admin/myaccount";
	}
    }

    @RequestMapping(value = "/all-accounts", method = RequestMethod.GET)
    public String home(Model model) {
	List<Account> accounts = adminService.getAllAccounts();
	model.addAttribute("accounts", accounts);
	return "admin/all-accounts";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String removeAccount(@RequestParam("id") int id) {
	try {
	    adminService.removeAccount(id);
	    return "redirect:/admin/all-accounts";
	} catch (InvalidUserInputException e) {
	    return "redirect:/boom";
	}
	// FIXME error message
    }

}
