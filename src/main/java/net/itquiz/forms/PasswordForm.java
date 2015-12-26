package net.itquiz.forms;

import net.itquiz.exceptions.InvalidUserInputException;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Artur Meshcheriakov
 */
public class PasswordForm implements IForm {

    protected String password;
    protected String passwordConfirmed;
    protected String oldPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        if (StringUtils.isBlank(password)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("password.required", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (password.length() > 60) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("volume.exceed", new Object[]{}, LocaleContextHolder.getLocale()));
        }
        if (!StringUtils.equals(password, passwordConfirmed)) {
            throw new InvalidUserInputException(
                    messageSource.getMessage("passwords.not.match", new Object[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
