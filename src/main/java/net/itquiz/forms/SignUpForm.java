package net.itquiz.forms;

import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.utils.PasswordChecker;
import org.springframework.context.MessageSource;

/**
 * @author Artur Meshcheriakov
 */

public class SignUpForm extends AccountInfoForm implements Copyable<Account> {

    private static final long serialVersionUID = 2155252411443776689L;

    protected String password;
    protected String passwordConfirmed;
    protected Boolean active;
    protected Boolean confirmed;

    public SignUpForm() {
        super();
        this.active = Boolean.TRUE;
        this.confirmed = Boolean.FALSE;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        super.validate(messageSource);
        PasswordChecker.checkPasswords(password, passwordConfirmed, messageSource);
    }

    @Override
    public void copyFieldsTo(Account account) {
        account.setEmail(email);
        account.setLogin(login);
        account.setPassword(password);
        account.setFio(fio);
        account.setConfirmed(confirmed);
        account.setActive(active);
    }

}
