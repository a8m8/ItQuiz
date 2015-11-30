package ua.com.itquiz.forms;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import ua.com.itquiz.entities.Account;
import ua.com.itquiz.exceptions.InvalidUserInputException;

/**
 *
 * @author Artur Meshcheriakov
 */

public class SingUpForm extends EmailForm implements Copyable<Account> {

    private static final long serialVersionUID = 2155252411443776689L;

    private String login;
    private String password;
    private String password2;
    private String fio;
    private Boolean confirmed = Boolean.FALSE;

    public Boolean getConfirmed() {
	return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
	this.confirmed = confirmed;
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

    public String getPassword2() {
	return password2;
    }

    public void setPassword2(String password2) {
	this.password2 = password2;
    }

    public String getFio() {
	return fio;
    }

    public void setFio(String fio) {
	this.fio = fio;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
	super.validate(messageSource);
	if (StringUtils.isBlank(login)) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("login.required", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (login.length() > 60) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("volume.exceed", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (StringUtils.isBlank(fio)) {
	    throw new InvalidUserInputException(messageSource.getMessage("fio.required",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (fio.length() > 200) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("volume.exceed", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (StringUtils.isBlank(password)) {
	    throw new InvalidUserInputException(messageSource.getMessage("passwords.required",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (password.length() > 60) {
	    throw new InvalidUserInputException(
		    messageSource.getMessage("volume.exceed", new Object[] {}, LocaleContextHolder.getLocale()));
	}
	if (!StringUtils.equals(password, password2)) {
	    throw new InvalidUserInputException(messageSource.getMessage("passwords.notmatch",
		new Object[] {}, LocaleContextHolder.getLocale()));
	}
    }

    @Override
    public void copyFieldsTo(Account account) {
	account.setEmail(email);
	account.setLogin(login);
	account.setPassword(password);
	account.setFio(fio);
	account.setConfirmed(confirmed);
    }

}
