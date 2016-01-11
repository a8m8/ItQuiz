package net.itquiz.forms.admin;

import net.itquiz.entities.Account;
import net.itquiz.exceptions.InvalidUserInputException;
import net.itquiz.forms.Copyable;
import net.itquiz.forms.utils.PasswordChecker;
import org.springframework.context.MessageSource;

/**
 * @author Artur Meshcheriakov
 */
public class AdminAddUserForm extends AdminUserForm implements Copyable<Account> {

    protected String password;
    protected String passwordConfirmed;

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

    @Override
    public void validate(MessageSource messageSource) throws InvalidUserInputException {
        super.validate(messageSource);
        PasswordChecker.checkPasswords(password, passwordConfirmed, messageSource);
    }

    @Override
    public void copyFieldsTo(Account account) {
        account.setEmail(email);
        account.setLogin(login);
        account.setFio(fio);
        account.setConfirmed(confirmed);
        account.setActive(active);
    }

}
