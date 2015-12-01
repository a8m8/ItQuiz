package ua.com.itquiz.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;
import ua.com.itquiz.forms.AccountInfoForm;
import ua.com.itquiz.services.CommonService;

/**
 *
 * @author Artur Meshcheriakov
 */
public class CommonController extends AbstractController {

    @Autowired
    CommonService commonService;

    @Autowired
    MessageSource messageSource;

    protected String showMyAccount(HttpSession session, Model model) {
	int id = (int) session.getAttribute("CURRENT_ACCOUNT_ID");
	Account account = commonService.getAccountById(id);
	model.addAttribute("personalInfoForm", new AccountInfoForm());
	model.addAttribute("account", account);
	return "myaccount";
    }

    protected String editPersonalData(HttpSession session, AccountInfoForm accountInfoForm)
	    throws InvalidUserInputException {
	accountInfoForm.validate(messageSource);
	Account account = commonService.getAccountById((int) session.getAttribute("CURRENT_ACCOUNT_ID"));

	if (!commonService.editPersonalData(account, accountInfoForm)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("nothing.save", new Object[] {}, LocaleContextHolder.getLocale()));
	}

	return messageSource.getMessage("changes.saved", new Object[] {}, LocaleContextHolder.getLocale());
    }

}
