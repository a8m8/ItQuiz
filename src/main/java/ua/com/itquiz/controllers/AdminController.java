package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.admin.AdminAddUserForm;
import ua.com.itquiz.forms.admin.AdminUserForm;
import ua.com.itquiz.forms.admin.PasswordForm;
import ua.com.itquiz.services.AdminService;
import ua.com.itquiz.services.CommonService;
import ua.com.itquiz.utils.Pagination;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CommonService commonService;

    @Value("${admin.pagination.count}")
    private int paginationCount;

    @RequestMapping(value = "/accounts/page/{pageNumber}", method = RequestMethod.GET)
    public String showAllAccounts(@PathVariable int pageNumber, Model model) {

        Pagination pagination = new Pagination(paginationCount, adminService.accountsCount(), pageNumber);
        List<Account> accounts = adminService.getAccounts(pagination.getOffset(), pagination.getCount());
        model.addAttribute("accounts", accounts);
        model.addAttribute("location", "/admin/accounts");
        model.addAttribute("pagination", pagination);

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
            setMessage(session, "admin.user.created");
            return "redirect:/admin/accounts/page/1";
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/add-user";
        }
    }

    @RequestMapping(value = "/accounts/delete", method = RequestMethod.GET)
    public String removeAccount(@RequestParam("id") int id, HttpSession session) {
        try {
            adminService.removeAccount(id);
            setMessage(session, "user.delete.successful");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/accounts/page/1";
    }

    @RequestMapping(value = "/accounts/edit-account", method = RequestMethod.GET)
    public String showEditUser(@RequestParam("id") int id, Model model) {
        AdminUserForm adminUserForm = adminService.generateFormBasedOnAccount(id);
        model.addAttribute("adminUserForm", adminUserForm);
        model.addAttribute("idAccount", id);
        return "admin/edit-account";
    }

    @RequestMapping(value = "/accounts/edit-account", method = RequestMethod.POST)
    public String editAccount(@ModelAttribute("adminUserForm") AdminUserForm adminUserForm,
                              @RequestParam("id") int id, HttpSession session) {
        try {
            adminUserForm.validate(messageSource);
            adminService.editUser(id, adminUserForm);
            setMessage(session, "changes.saved");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/accounts/page/1";
    }

    @RequestMapping(value = "/accounts/edit-account/change-password", method = RequestMethod.GET)
    public String showUserChangePassword(@RequestParam("id") int id, Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("idAccount", id);
        model.addAttribute("object", "accounts/edit-account");
        model.addAttribute("role", "admin");
        return "admin/change-password";
    }

    @RequestMapping(value = "/accounts/edit-account/change-password", method = RequestMethod.POST)
    public String editUserPassword(@ModelAttribute("passwordForm") PasswordForm passwordForm, @RequestParam("id")
    int id, HttpSession session) {
        try {
            passwordForm.validate(messageSource);
            commonService.changePassword(id, passwordForm);
            setMessage(session, "password.changed");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/accounts/edit-account?id=" + id;
    }

}
