package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.itquiz.constants.ApplicationConstants;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.AccountRole;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.forms.AdminAddUserForm;
import ua.com.itquiz.forms.AdminUserForm;
import ua.com.itquiz.forms.PasswordForm;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.AdminService;
import ua.com.itquiz.services.CommonService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Autowired
    protected AdminService adminService;

    @Autowired
    protected CommonService commonService;

    @Autowired
    protected MessageSource messageSource;

    @RequestMapping(value = "/myaccount", method = RequestMethod.GET)
    protected String showMyAccount(HttpSession session, Model model) {
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }
        if (session.getAttribute("errorMessage") != null) {
            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }
        model.addAttribute("personalInfoForm", new AccountInfoForm());
        model.addAttribute("account", commonService.getAccountById(SecurityUtils.getCurrentIdAccount()));
        return "admin/myaccount";
    }

    @RequestMapping(value = "/myaccount", method = RequestMethod.POST)
    public String editPersonalInformation(
            @ModelAttribute("personalInfoForm") AccountInfoForm accountInfoForm, Model model,
            HttpSession session) {
        try {
            accountInfoForm.validate(messageSource);
            commonService.editPersonalData(SecurityUtils.getCurrentIdAccount(), accountInfoForm);
            setMessage(session, "changes.saved");
            return "redirect:/admin/accounts/page/1";
        } catch (InvalidUserInputException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "admin/myaccount";
        }
    }

    @RequestMapping(value = "/myaccount/change-password", method = RequestMethod.GET)
    public String showChangePassword(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("object", "myaccount");
        return "admin/change-password";
    }

    @RequestMapping(value = "/myaccount/change-password", method = RequestMethod.POST)
    public String editPassword(@ModelAttribute("passwordForm") PasswordForm passwordForm, HttpSession session) {
        try {
            passwordForm.validate(messageSource);
            commonService.changePassword(SecurityUtils.getCurrentIdAccount(), passwordForm);
            setMessage(session, "password.changed");
            return "redirect:/admin/myaccount";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/myaccount";
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
        long temp = adminService.accountsCount() / count;
        long maximum = (adminService.accountsCount() % count == 0) ? temp : temp + 1;
        int current = pageNumber;
        int begin = Math.max(1, current - 5);
        long end = Math.min(begin + 10, maximum);

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
        model.addAttribute("adminAddUserForm", new AdminAddUserForm());
        return "admin/add-user";
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("adminAddUserForm") AdminAddUserForm adminAddUserForm,
                             Model model, HttpSession session) {
        try {
            adminAddUserForm.validate(messageSource);
            adminService.addUser(adminAddUserForm);
            setMessage(session, "admin.usercreated");
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
            setMessage(session, "delete.successful");
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
            adminService.editUser(id, adminUserForm);
            setMessage(session, "changes.saved");
            return "redirect:/admin/accounts/page/1";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/accounts/page/1";
        }
    }

    @RequestMapping(value = "/edit-account/change-password", method = RequestMethod.GET)
    public String showUserChangePassword(@RequestParam("id") int id, Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("idAccount", id);
        model.addAttribute("object", "edit-account");
        return "admin/change-password";
    }

    @RequestMapping(value = "/edit-account/change-password", method = RequestMethod.POST)
    public String editUserPassword(@ModelAttribute("passwordForm") PasswordForm passwordForm, @RequestParam("id")
    int id, HttpSession session) {
        try {
            passwordForm.validate(messageSource);
            commonService.changePassword(id, passwordForm);
            setMessage(session, "password.changed");
            return "redirect:/admin/edit-account?id=" + id;
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/edit-account?id=" + id;
        }
    }

}
