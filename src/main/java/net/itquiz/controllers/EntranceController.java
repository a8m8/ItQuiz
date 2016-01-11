package net.itquiz.controllers;

import net.itquiz.constants.ApplicationConstants;
import net.itquiz.entities.Account;
import net.itquiz.entities.Role;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.EmailForm;
import net.itquiz.forms.PasswordForm;
import net.itquiz.forms.SignUpForm;
import net.itquiz.security.CurrentAccount;
import net.itquiz.security.SecurityUtils;
import net.itquiz.services.CommonService;
import net.itquiz.services.EntranceService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        redirects.put(ApplicationConstants.ADVANCED_TUTOR_ROLE, "/advanced-tutor/alltests/page/1");
        redirects.put(ApplicationConstants.TUTOR_ROLE, "/tutor/mytests/page/1");
        redirects.put(ApplicationConstants.STUDENT_ROLE, "/student/tests/page/1");
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntranceService entranceService;

    @Autowired
    private CommonService commonService;

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
            return "redirect:/crossing";
        }
        initRoles(model);
        return "login";
    }

    @RequestMapping(value = "/crossing", method = RequestMethod.GET)
    public String crossing(HttpSession session) {
        try {
            CurrentAccount currentAccount = SecurityUtils.getCurrentAccount();
            entranceService.checkAccess(currentAccount.getRole(), SecurityUtils.getCurrentIdAccount());
            return "redirect:" + redirects.get(currentAccount.getRole());
        } catch (InvalidUserInputException e) {
            SecurityContextHolder.clearContext();
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/loginFailed";
        }
    }

    private void initRoles(Model model) {
        List<Role> roles = entranceService.listRoles();
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
            entranceService.sendPasswordRecoveryEmail(passwordRecoveryForm.getEmail());
            setMessage(session, "password.send");
            return "redirect:/login";
        } catch (InvalidUserInputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "password-recovery";
        }
    }

    @RequestMapping(value = "password-recovery/check", method = RequestMethod.GET)
    public String recoveryCheckCredentials(@RequestParam("id") int id,
                                           @RequestParam("passHash") String passHash, HttpSession session) {
        try {
            entranceService.checkPasswordRecoveryAccess(id, passHash);
            session.setAttribute("accountId", id);
            return "redirect:/password-recovery/account/change-password";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "password-recovery/account/change-password", method = RequestMethod.GET)
    public String recoveryShowChangePassword(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("role", "password-recovery");
        model.addAttribute("pageName", "account");
        return "change-password";
    }

    @RequestMapping(value = "/password-recovery/account/change-password", method = RequestMethod.POST)
    public String recoveryChangePassword(Model model, @ModelAttribute("passwordForm") PasswordForm passwordForm,
                                         HttpSession session) {
        try {
            passwordForm.validate(messageSource);
            commonService.changePassword((int) session.getAttribute("accountId"), passwordForm, false);
            setMessage(session, "password.changed");
            session.removeAttribute("accountId");
            return "redirect:/login";
        } catch (InvalidUserInputException e) {
            model.addAttribute("role", "password-recovery");
            model.addAttribute("pageName", "account");
            model.addAttribute("errorMessage", e.getMessage());
            return "change-password";
        }
    }

}
