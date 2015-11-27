package ua.com.itquiz.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.itquiz.constants.Roles;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.entities.Role;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.LoginForm;
import ua.com.itquiz.forms.PasswordRecoveryForm;
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
	redirects.put(Roles.ADMIN_ROLE.getID(), "/admin/home");
	redirects.put(Roles.ADVANCED_TUTOR_ROLE.getID(), "/advanced_tutor/home");
	redirects.put(Roles.TUTOR_ROLE.getID(), "/tutor/home");
	redirects.put(Roles.STUDENT_ROLE.getID(), "/home");
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntranceService entranceService;

    @RequestMapping(value = "/login/account_confirmation", method = RequestMethod.GET)
    public String confirmAccount(@RequestParam("id") int id, @RequestParam("hash") String hash) {
	try {
	    entranceService.verifyAccount(id, hash);
	    return "redirect:";
	} catch (InvalidUserInputException e) {
	    return "boom";
	}
	// FIXME DISPLAING MESSAGE!!! and BindingResult
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
	model.addAttribute("loginForm", new LoginForm());
	initRoles(model);
	return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Model model,
	    HttpSession session) {
	try {
	    loginForm.validate(messageSource);
	    Account account = entranceService.login(loginForm.getLogin(), loginForm.getPassword(),
		    loginForm.getIdRole());
	    session.setAttribute("CURRENT_ACCOUNT_ID", account.getIdAccount());
	    return "redirect:" + redirects.get(loginForm.getIdRole());
	} catch (InvalidUserInputException e) {
	    result.addError(new ObjectError("", e.getMessage()));
	    initRoles(model);
	    return "login";
	}
    }

    private void initRoles(Model model) {
	List<Role> roles = entranceService.getAllRoles();
	model.addAttribute("roles", roles);
    }

    @RequestMapping(value="/singup",method=RequestMethod.GET)
    public String showSingUp(Model model) {
	model.addAttribute("singUpForm", new SingUpForm());
	return "singup";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.POST)
    public String singup(@ModelAttribute("singUpForm") SingUpForm singUpForm, BindingResult result, Model model) {
	try {
	    singUpForm.validate(messageSource);
	    entranceService.singUp(singUpForm);
	    return "redirect:login";
	} catch (InvalidUserInputException ex) {
	    result.addError(new ObjectError("", ex.getMessage()));
	    return "singup";
	}
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.GET)
    public String showPasswordRecovery(Model model) {
	model.addAttribute("passwordRecoveryForm", new PasswordRecoveryForm());
	return "password-recovery";
    }

    @RequestMapping(value = "/password-recovery", method = RequestMethod.POST)
    public String passwordRecovery(@ModelAttribute("passwordRecoveryForm") PasswordRecoveryForm passwordRecoveryForm,
	    BindingResult result, Model model) {
	try {
	    passwordRecoveryForm.validate(messageSource);
	    entranceService.sendPasswordForRecovery(passwordRecoveryForm.getLogin());
	    return "redirect:login";
	} catch (InvalidUserInputException ex) {
	    result.addError(new ObjectError("", ex.getMessage()));
	    return "password-recovery";
	}
    }
}
