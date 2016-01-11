package net.itquiz.forms;

import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.utils.PasswordChecker;
import org.springframework.context.MessageSource;

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
        PasswordChecker.checkPasswords(password, passwordConfirmed, messageSource);
    }
}
