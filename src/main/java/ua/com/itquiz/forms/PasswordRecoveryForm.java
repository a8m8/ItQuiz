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
    private String email;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
	if (StringUtils.isBlank(email)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("email.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
    }

}
