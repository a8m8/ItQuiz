package ua.com.itquiz.forms;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 *
 * @author Artur Meshcheriakov
 */
public class LoginForm implements IForm {

    private static final long serialVersionUID = -6655327847890728394L;

    private String login;
    private String password;
    private int idRole;

    public int getIdRole() {
	return idRole;
    }

    public void setIdRole(int idRole) {
	this.idRole = idRole;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
	if(StringUtils.isBlank(login)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (StringUtils.isBlank(password)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("password.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (idRole == 0) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("role.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
    }
}
