package ua.com.itquiz.forms;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 *
 * @author Artur Meshcheriakov
 */
public class PasswordRecoveryForm implements IForm {

    private static final long serialVersionUID = -4089527274880027630L;
    private String login;

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
	if (StringUtils.isBlank(login)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
    }

}
