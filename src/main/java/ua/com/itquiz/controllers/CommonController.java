package ua.com.itquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.security.SecurityUtils;
import ua.com.itquiz.services.CommonService;

import javax.servlet.http.HttpSession;

/**
 * @author Artur Meshcheriakov
 */
public class CommonController extends AbstractController {

    @Autowired
    CommonService commonService;

    @Autowired
    MessageSource messageSource;

    protected String showMyAccount(HttpSession session, Model model) {
        Account account = commonService.getAccountById(SecurityUtils.getCurrentIdAccount());
        model.addAttribute("personalInfoForm", new AccountInfoForm());
        model.addAttribute("account", account);
        return "myaccount";
    }

    protected String editPersonalData(HttpSession session, AccountInfoForm accountInfoForm)
            throws InvalidUserInputException {
        accountInfoForm.validate(messageSource);
        Account account = commonService.getAccountById(SecurityUtils.getCurrentIdAccount());

        if (!commonService.editPersonalData(account, accountInfoForm)) {
            throw new InvalidUserInputException(messageSource.getMessage("nothing.save",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }

        return messageSource.getMessage("changes.saved", new Object[]{},
                LocaleContextHolder.getLocale());
    }

}
