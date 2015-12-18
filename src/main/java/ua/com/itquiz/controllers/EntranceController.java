package ua.com.itquiz.controllers;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.itquiz.constants.ApplicationConstants;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.admin.EmailForm;
import ua.com.itquiz.forms.admin.SignUpForm;
import ua.com.itquiz.security.CurrentAccount;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.EntranceService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artur Meshcheriakov
 */
@Controller
public class EntranceController extends AbstractController implements InitializingBean {

    private final Map<Short, String> redirects = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        redirects.put(ApplicationConstants.ADMIN_ROLE, "/admin/accounts/page/1");
        redirects.put(ApplicationConstants.ADVANCED_TUTOR_ROLE, "/advanced-tutor/mytests");
        redirects.put(ApplicationConstants.TUTOR_ROLE, "/tutor/mytests/page/1");
        redirects.put(ApplicationConstants.STUDENT_ROLE, "/student/tests");
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntranceService entranceService;

    @RequestMapping(value = "/account-confirmation", method = RequestMethod.GET)
    public String confirmAccount(@RequestParam("id") int id, @RequestParam("hash") String hash,
                                 HttpSession session) {
        try {
            entranceService.verifyAccount(id, hash);
            setMessage(session, "confirmation.success");
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/login";
    }

    @RequestMapping(value = {"/login", "/loginFailed"}, method = RequestMethod.GET)
    public String showLogin(Model model) {
        if (SecurityUtils.getCurrentAccount() != null) {
            return "redirect:crossing";
        }
        initRoles(model);
        return "login";
    }

    @RequestMapping(value = "/crossing", method = RequestMethod.GET)
    public String crossing() {
        CurrentAccount currentAccount = SecurityUtils.getCurrentAccount();
        return "redirect:" + redirects.get(currentAccount.getRole());
    }

    private void initRoles(Model model) {
        List<Role> roles = entranceService.getAllRoles();
        model.addAttribute("roles", roles);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    String verify(@RequestParam(value = "email", required = false) String email,
                  @RequestParam(value = "login", required = false) String login) {
        if (email != null) {
            return (entranceService.isEmailExist(email)) ? "false" : "true";
        }
        if (login != null) {
            return (entranceService.isLoginExist(login)) ? "false" : "true";
        }
        return "Wrong parameter";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSingUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String singup(@ModelAttribute("signUpForm") SignUpForm signUpForm, Model model,
                         HttpSession session) {
        try {
            signUpForm.validate(messageSource);
            entranceService.signUp(signUpForm);
            setMessage(session, "signup.email.send");
            return "redirect:/login";
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    String verifyPasswordRecovery(@RequestParam("email") String email) {
        Account account = entranceService.getAccount(email);
        if (account == null || !account.getActive() || !account.getConfirmed()) {
            return "false";
        }
        return "true";
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.GET)
    public String showPasswordRecovery(Model model) {
        model.addAttribute("passwordRecoveryForm", new EmailForm());
        return "password-recovery";
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.POST)
    public String passwordRecovery(
            @ModelAttribute("passwordRecoveryForm") EmailForm passwordRecoveryForm, Model model,
            HttpSession session) {
        try {
            passwordRecoveryForm.validate(messageSource);
            entranceService.sendPasswordForRecovery(passwordRecoveryForm.getEmail());
            setMessage(session, "password.send");
            return "redirect:/login";
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "password-recovery";
        }
    }
}
