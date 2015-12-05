package ua.com.itquiz.controllers;

import java.util.HashSet;
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

import ua.com.itquiz.constants.ApplicationConstants;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRole;
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
    public String editPersonalInformation(
	@ModelAttribute("personalInfoForm") AccountInfoForm accountInfoForm, Model model,
	HttpSession session) {
	try {
	    session.setAttribute("message", super.editPersonalData(session, accountInfoForm));
	    return "redirect:/admin/accounts/page/1";
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
	if (session.getAttribute("errorMessage") != null) {
	    model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
	    session.removeAttribute("errorMessage");
	}

	int count = 1; // Count of accounts which are displayed at the page
	int temp = adminService.accountCount() / count;
	int maximum = (adminService.accountCount() % count == 0) ? temp : temp + 1;
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
    public String addNewUser(@ModelAttribute("adminUserForm") AdminUserForm adminUserForm,
	Model model, HttpSession session) {
	try {
	    adminUserForm.validate(messageSource);
	    adminService.addUser(adminUserForm);
	    session.setAttribute("message", messageSource.getMessage("admin.usercreated",
		new Object[] {}, LocaleContextHolder.getLocale()));
	    return "redirect:/admin/accounts/page/1";
	} catch (InvalidUserInputException e) {
	    model.addAttribute("errorMessage", e.getMessage());
	    return "admin/add-user";
	}
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String removeAccount(@RequestParam("id") int id, HttpSession session) {
	try {
	    adminService.removeAccount(id);
	    return "redirect:/admin/accounts/page/1";
	} catch (InvalidUserInputException e) {
	    session.setAttribute("errorMessage", e.getMessage());
	    return "redirect:/admin/accounts/page/1";
	}
    }

    @RequestMapping(value = "/edit-account", method = RequestMethod.GET)
    public String showEditUser(@RequestParam("id") int id, Model model) {
	Account account = commonService.getAccountById(id);
	AdminUserForm adminUserForm = new AdminUserForm();
	if (account.getActive()) {
	    adminUserForm.setActive(Boolean.TRUE);
	}
	if (account.getConfirmed()) {
	    adminUserForm.setConfirmed(Boolean.TRUE);
	}
	HashSet<Integer> accountRolesSet = new HashSet<>();
	for (AccountRole accountRole : account.getAccountRoles()) {
	    accountRolesSet.add(accountRole.getRole().getIdRole().intValue());
	}
	if (accountRolesSet.contains(ApplicationConstants.ADMIN_ROLE)) {
	    adminUserForm.setAdministrator(Boolean.TRUE);
	}
	if (accountRolesSet.contains(ApplicationConstants.ADVANCED_TUTOR_ROLE)) {
	    adminUserForm.setAdvancedTutor(Boolean.TRUE);
	}
	if (accountRolesSet.contains(ApplicationConstants.TUTOR_ROLE)) {
	    adminUserForm.setTutor(Boolean.TRUE);
	}
	if (accountRolesSet.contains(ApplicationConstants.STUDENT_ROLE)) {
	    adminUserForm.setStudent(Boolean.TRUE);
	}
	model.addAttribute("adminUserForm", adminUserForm);
	model.addAttribute("user", account);
	return "admin/edit-account";
    }

    @RequestMapping(value = "/edit-account", method = RequestMethod.POST)
    public String editAccount(@ModelAttribute("adminUserForm") AdminUserForm adminUserForm,
	@RequestParam("id") int id, Model model, HttpSession session) {
	try {
	    adminUserForm.validate(messageSource);
	} catch (InvalidUserInputException e) {

	}
	// TODO EDIT USER CONTROLLER
	return null;
    }

}
