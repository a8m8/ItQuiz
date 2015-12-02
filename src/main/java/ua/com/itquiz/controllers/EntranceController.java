package ua.com.itquiz.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.itquiz.constants.Roles;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.EmailForm;
import ua.com.itquiz.forms.LoginForm;
import ua.com.itquiz.forms.SingUpForm;
import ua.com.itquiz.services.EntranceService;

/**
 *
 * @author Artur Meshcheriakov
 */
@Controller
public class EntranceController extends AbstractController implements InitializingBean {

    private final Map<Integer, String> redirects = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
	redirects.put(Roles.ADMIN_ROLE.getID(), "/admin/accounts/page/1");
	redirects.put(Roles.ADVANCED_TUTOR_ROLE.getID(), "/advanced-tutor/home");
	redirects.put(Roles.TUTOR_ROLE.getID(), "/tutor/home");
	redirects.put(Roles.STUDENT_ROLE.getID(), "/home");
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntranceService entranceService;

    @RequestMapping(value = "/verify-email", method = RequestMethod.GET)
    public @ResponseBody String verifyEmail(@RequestParam("email") String email) {
	return (entranceService.isEmailExist(email)) ? "false" : "true";
    }

    @RequestMapping(value = "/verify-email-recov", method = RequestMethod.GET)
    public @ResponseBody String verifyEmailRec(@RequestParam("email") String email) {
	Account account = entranceService.getAccount(email);

	if (account == null || !account.getActive() || !account.getConfirmed()) {
	    return "false";
	}

	return "true";
    }

    @RequestMapping(value = "/verify-login", method = RequestMethod.GET)
    public @ResponseBody String verifyLogin(@RequestParam("login") String login) {
	return (entranceService.isLoginExist(login)) ? "false" : "true";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
	session.invalidate();
	return "redirect:login";
    }

    @RequestMapping(value = "/account-confirmation", method = RequestMethod.GET)
    public String confirmAccount(@RequestParam("id") int id, @RequestParam("hash") String hash, HttpSession session) {
	try {
	    entranceService.verifyAccount(id, hash);
	    session.setAttribute("message",
		    messageSource.getMessage("confirmation.success", new Object[] {}, LocaleContextHolder.getLocale()));
	    return "redirect:login";
	} catch (InvalidUserInputException e) {
	    session.setAttribute("errorMessage", e.getMessage());
	    return "redirect:login";
	}
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(HttpSession session, Model model) {
	if (session.getAttribute("message") != null) {
	    model.addAttribute("message", session.getAttribute("message"));
	    session.removeAttribute("message");
	}
	if (session.getAttribute("errorMessage") != null) {
	    model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
	    session.removeAttribute("errorMessage");
	}
	model.addAttribute("loginForm", new LoginForm());
	initRoles(model);
	return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model, HttpSession session) {
	try {
	    loginForm.validate(messageSource);
	    Account account = entranceService.login(loginForm.getEmail(), loginForm.getPassword(),
		    loginForm.getIdRole());
	    session.setAttribute("CURRENT_ACCOUNT_ID", account.getIdAccount());
	    return "redirect:" + redirects.get(loginForm.getIdRole());
	} catch (InvalidUserInputException e) {
	    model.addAttribute("errorMessage", e.getMessage());
	    initRoles(model);
	    return "login";
	}
    }

    private void initRoles(Model model) {
	List<Role> roles = entranceService.getAllRoles();
	model.addAttribute("roles", roles);
    }

    @RequestMapping(value = "/singup", method = RequestMethod.GET)
    public String showSingUp(Model model) {
	model.addAttribute("singUpForm", new SingUpForm());
	return "singup";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.POST)
    public String singup(@ModelAttribute("singUpForm") SingUpForm singUpForm, Model model, HttpSession session) {
	try {
	    singUpForm.validate(messageSource);
	    entranceService.singUp(singUpForm);
	    session.setAttribute("message",
		    messageSource.getMessage("singup.emailsend", new Object[] {}, LocaleContextHolder.getLocale()));
	    return "redirect:login";
	} catch (InvalidUserInputException e) {
	    model.addAttribute("errorMessage", e.getMessage());
	    return "singup";
	}
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.GET)
    public String showPasswordRecovery(Model model) {
	model.addAttribute("passwordRecoveryForm", new EmailForm());
	return "password-recovery";
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.POST)
    public String passwordRecovery(@ModelAttribute("passwordRecoveryForm") EmailForm passwordRecoveryForm, Model model,
	    HttpSession session) {
	try {
	    passwordRecoveryForm.validate(messageSource);
	    entranceService.sendPasswordForRecovery(passwordRecoveryForm.getEmail());
	    session.setAttribute("message",
		    messageSource.getMessage("password.send", new Object[] {}, LocaleContextHolder.getLocale()));
	    return "redirect:login";
	} catch (InvalidUserInputException e) {
	    model.addAttribute("errorMessage", e.getMessage());
	    return "password-recovery";
	}
    }
}
