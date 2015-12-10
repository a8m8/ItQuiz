package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.forms.PasswordForm;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.CommonService;

import javax.servlet.http.HttpSession;

/**
 * @author Artur Meshcheriakov
 */
@Controller
public class CommonController extends AbstractController {

    @Autowired
    CommonService commonService;

    @RequestMapping(value = "{role}/myaccount", method = RequestMethod.GET)
    protected String showMyAccount(@PathVariable String role, HttpSession session, Model model) {
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }
        if (session.getAttribute("errorMessage") != null) {
            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }
        AccountInfoForm accountInfoForm = commonService.generateAccountForm(SecurityUtils.getCurrentIdAccount());
        model.addAttribute("personalInfoForm", accountInfoForm);
        model.addAttribute("role", role);
        return role + "/myaccount";
    }

    @RequestMapping(value = "{role}/myaccount", method = RequestMethod.POST)
    public String editPersonalInformation(@PathVariable String role,
                                          @ModelAttribute("personalInfoForm") AccountInfoForm accountInfoForm,
                                          Model model, HttpSession session) {
        try {
            accountInfoForm.validate(messageSource);
            commonService.editPersonalData(SecurityUtils.getCurrentIdAccount(), accountInfoForm);
            setMessage(session, "changes.saved");
            return role + "/myaccount";
        } catch (InvalidUserInputException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return role + "/myaccount";
        }
    }

    @RequestMapping(value = "{role}/myaccount/change-password", method = RequestMethod.GET)
    public String showChangePassword(@PathVariable String role, Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("object", "myaccount");
        return role + "/change-password";
    }

    @RequestMapping(value = "{role}/myaccount/change-password", method = RequestMethod.POST)
    public String editPassword(@ModelAttribute("passwordForm") PasswordForm passwordForm,
                               @PathVariable String role,
                               HttpSession session) {
        try {
            passwordForm.validate(messageSource);
            commonService.changePassword(SecurityUtils.getCurrentIdAccount(), passwordForm);
            setMessage(session, "password.changed");
            return "redirect:/" + role + "/myaccount";
        } catch (InvalidUserInputException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/" + role + "/myaccount";
        }
    }

}
