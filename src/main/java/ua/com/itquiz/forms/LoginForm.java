package ua.com.itquiz.forms;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 *
 * @author Artur Meshcheriakov
 */
public class LoginForm extends EmailForm {

    private static final long serialVersionUID = -6655327847890728394L;

    private String password;
    private int idRole;

    public int getIdRole() {
	return idRole;
    }

    public void setIdRole(int idRole) {
	this.idRole = idRole;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
	super.validate(messageSource);
	if (StringUtils.isBlank(password)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("password.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (password.length() > 60) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("passwords.notmatch", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (idRole == 0) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("role.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
    }
}
