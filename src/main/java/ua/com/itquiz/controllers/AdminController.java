package ua.com.itquiz.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.forms.AdminUserForm;
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

    @RequestMapping(value = "/accounts/page/{pageNumber}", method = RequestMethod.GET)
    public String showAllAccounts(@PathVariable int pageNumber, HttpSession session, Model model) {
	if (session.getAttribute("message") != null) {
	    model.addAttribute("message", session.getAttribute("message"));
	    session.removeAttribute("message");
	}
	int count = 1;
	int maximum = new BigDecimal(adminService.accountCount() / count).setScale(0, RoundingMode.UP).intValue();
	int current = pageNumber;
	int begin = Math.max(1, current - 5);
	int end = Math.min(begin + 10, maximum);

	List<Account> accounts = adminService.getAccounts(((pageNumber - 1) * count), count);
	model.addAttribute("accounts", accounts);
	model.addAttribute("beginIndex", begin);
	model.addAttribute("endIndex", end);
	model.addAttribute("currentIndex", current);
	model.addAttribute("maximum", maximum);

	return "admin/accounts";
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.GET)
    public String showAddUser(Model model) {
	model.addAttribute("adminUserForm", new AdminUserForm());
	return "admin/add-user";
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("adminUserForm") AdminUserForm adminUserForm, Model model,
	    HttpSession session) {
	try {
	    adminUserForm.validate(messageSource);
	    adminService.addUser(adminUserForm);
	    session.setAttribute("message",
		    messageSource.getMessage("admin.usercreated", new Object[] {}, LocaleContextHolder.getLocale()));
	    return "redirect:/admin/accounts/page/1";
	} catch (InvalidUserInputException e) {
	    model.addAttribute("errorMessage", e.getMessage());
	    return "admin/add-user";
	}
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String removeAccount(@RequestParam("id") int id) {
	try {
	    adminService.removeAccount(id);
	    return "redirect:/admin/accounts/page/1";
	} catch (InvalidUserInputException e) {
	    return "redirect:/boom";
	}
	// FIXME error message
    }

}
